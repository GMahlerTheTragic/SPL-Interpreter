package interpreter.visitor;

import frontend.SPLParser.*;
import interpreter.Environment;
import interpreter.Value;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;


/**
 * This class provides an empty implementation of {@link SPLVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 */
@SuppressWarnings("CheckReturnValue")
public class SPLVisitorImpl extends AbstractParseTreeVisitor<Value> implements SPLVisitor {
    private final TokenStreamRewriter rewriter;

    private final Environment environment;

    public SPLVisitorImpl(TokenStream tokenStream) {
        this.environment = new Environment();
        this.rewriter = new TokenStreamRewriter(tokenStream);
    }

    public SPLVisitorImpl(TokenStream tokenStream, Environment environment) {
        this.environment = environment;
        this.rewriter = new TokenStreamRewriter(tokenStream);
    }

    /**
     * Entry point. Visit all declarations in program.
     */
    @Override
    public void visitProgram(ProgramContext ctx) {
        environment.init();
        for (DeclarationContext declarationContext : ctx.declaration()) {
            visitDeclaration(declarationContext);
        }
    }

    /**
     * Visit all variable declarations and statements in declaration
     */
    @Override
    public void visitDeclaration(DeclarationContext ctx) {
        if (ctx.varDecl() != null) {
            visitVarDecl(ctx.varDecl());
        }
        if (ctx.statement() != null) {
            visitStatement(ctx.statement());
        }
    }

    /**
     * extract identifier name and assign value if applicable in the environment
     */
    @Override
    public void visitVarDecl(VarDeclContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        if (ctx.expression() != null) {
            Value value = visitExpression(ctx.expression());
            // Handle variable assignment
            environment.assignVariable(identifier, value);
        }
    }

    /**
     * {@inheritDoc}
     * choose which kind of statement we have and dispatch
     */
    @Override
    public void visitStatement(StatementContext ctx) {
        if (ctx.exprStmt() != null) {
            visitExprStmt(ctx.exprStmt());
        }
        if (ctx.printStmt() != null) {
            visitPrintStmt(ctx.printStmt());
        }
        if (ctx.ifStmt() != null) {
            visitIfStmt(ctx.ifStmt());
        }
        if (ctx.whileStmt() != null) {
            visitWhileStmt(ctx.whileStmt());
        }
        if (ctx.block() != null) {
            visitBlock(ctx.block());
        }
    }

    /**
     * return value of expression
     */
    @Override
    public Value visitExprStmt(ExprStmtContext ctx) {
        return visitExpression(ctx.expression());
    }

    /**
     * implement if else rule taking care of scoping
     */
    @Override
    public void visitIfStmt(IfStmtContext ctx) {
        Value condition = visitExpression(ctx.expression());
        if (condition.isTrue()) {
            environment.enterNewScope();
            visitStatement(ctx.statement(0));
            environment.leaveCurrentScope();
        } else {
            environment.enterNewScope();
            if (ctx.statement(1) != null) {
                visitStatement(ctx.statement(1));
            }
            environment.leaveCurrentScope();
        }
    }

    /**
     * use map print statement to java println
     */
    @Override
    public void visitPrintStmt(PrintStmtContext ctx) {
        System.out.println(visitExpression(ctx.expression()));
    }

    /**
     * implement while rule taking care of scoping
     */
    @Override
    public void visitWhileStmt(WhileStmtContext ctx) {
        Value condition = visitExpression(ctx.expression());
        environment.enterNewScope();
        while (condition.isTrue()) {
            visitStatement(ctx.statement());
            condition = visitExpression(ctx.expression());
        }
        environment.leaveCurrentScope();
    }

    /**
     * essentially enter a new scope
     */
    @Override
    public void visitBlock(BlockContext ctx) {
        environment.enterNewScope();
        for (DeclarationContext declarationContext : ctx.declaration()) {
            visitDeclaration(declarationContext);
        }
        environment.leaveCurrentScope();
    }

    /**
     * just pass on to assignment
     */
    @Override
    public Value visitExpression(ExpressionContext ctx) {
        return visitAssignment(ctx.assignment());
    }

    /**
     * if variable assigment, assign value in environment, else pass on
     */
    @Override
    public Value visitAssignment(AssignmentContext ctx) {
        if (ctx.IDENTIFIER() != null) {
            environment.assignVariable(ctx.IDENTIFIER().getText(), visitAssignment(ctx.assignment()));
            return null;
        }
        return visitLogicOr(ctx.logic_or());
    }

    /**
     * {@inheritDoc}
     * implement logical "or" or hand on to logical and
     */
    @Override
    public Value visitLogicOr(Logic_orContext ctx) {
        if (ctx.logic_and().size() == 1) {
            return visitLogicAnd(ctx.logic_and(0));
        }
        Value result = Value.FALSE;
        for (Logic_andContext logicAndContext : ctx.logic_and()) {
            result = result.or(visitLogicAnd(logicAndContext));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * implement logical and or hand on to equality
     */
    @Override
    public Value visitLogicAnd(Logic_andContext ctx) {
        if (ctx.equality().size() == 1) {
            return visitEquality(ctx.equality(0));
        }
        Value result = Value.TRUE;
        for (EqualityContext equalityContext : ctx.equality()) {
            result = result.and(visitEquality(equalityContext));
        }
        return result;
    }

    /**
     * implement equality
     */
    @Override
    public Value visitEquality(EqualityContext ctx) {
        if (ctx.comparison().size() == 1) {
            return visitComparison(ctx.comparison(0));
        }
        Value eval = visitComparison(ctx.comparison(0));
        Value result = Value.TRUE;
        for (ComparisonContext comparison : ctx.comparison()) {
            Token previousToken = rewriter.getTokenStream().get(comparison.getStart().getTokenIndex() - 1);
            if (previousToken.getText().equals("==")) {
                result = (eval.eq(visitComparison(comparison)));
            }
            if (previousToken.getText().equals("!=")) {
                result = (eval.neq(visitComparison(comparison)));
            }
        }
        return result;
    }

    /**
     * implement comparison
     */
    @Override
    public Value visitComparison(ComparisonContext ctx) {
        if (ctx.term().size() == 1) {
            return visitTerm(ctx.term(0));
        }
        Value eval = visitTerm(ctx.term(0));
        Value result = Value.TRUE;
        for (TermContext term : ctx.term()) {
            Value other = visitTerm(term);
            Token previousToken = rewriter.getTokenStream().get(term.getStart().getTokenIndex() - 1);
            if (previousToken.getText().equals("<")) {
                result = eval.lt(other);
            }
            if (previousToken.getText().equals("<=")) {
                result = eval.lte(other);
            }
            if (previousToken.getText().equals(">")) {
                result = eval.gt(other);
            }
            if (previousToken.getText().equals(">=")) {
                result = eval.gte(other);
            }
        }
        return result;
    }

    /**
     * implement term
     */
    @Override
    public Value visitTerm(TermContext ctx) {
        if (ctx.factor().size() == 1) {
            return visitFactor(ctx.factor(0));
        }
        Value result = visitFactor(ctx.factor(0));
        for (FactorContext factor : ctx.factor()) {
            Value other = visitFactor(factor);
            Token previousToken = rewriter.getTokenStream().get(factor.getStart().getTokenIndex() - 1);
            if (previousToken.getText().equals("+")) {
                result = result.add(other);
            }
            if (previousToken.getText().equals("-")) {
                result = result.sub(other);
            }
        }
        return result;
    }

    /**
     * implement factor
     */
    @Override
    public Value visitFactor(FactorContext ctx) {
        if (ctx.unary().size() == 1) {
            return visitUnary(ctx.unary(0));
        }
        Value result = new Value(1);
        for (UnaryContext unary : ctx.unary()) {
            Value other = visitUnary(unary);
            Token previousToken = rewriter.getTokenStream().get(unary.getStart().getTokenIndex() - 1);
            if (previousToken.getText().equals("*")) {
                result = result.mul(other);
            }
            if (previousToken.getText().equals("/")) {
                result = result.div(other);
            }
        }
        return result;
    }

    /**
     * implement unary
     */
    @Override
    public Value visitUnary(UnaryContext ctx) {
        if (ctx.primary() != null) {
            return visitPrimary(ctx.primary());
        }
        Token previousToken = rewriter.getTokenStream().get(ctx.unary().getStart().getTokenIndex() - 1);
        if (previousToken.getText().equals("!")) {
            Value unary = visitUnary(ctx.unary());
            return unary.not();
        }
        Value unary = visitUnary(ctx.unary());
        return new Value(0).sub(unary);
    }

    /**
     * implement primary
     */
    @Override
    public Value visitPrimary(PrimaryContext ctx) {
        if (ctx.expression() != null) {
            return visitExpression(ctx.expression());
        }
        if (ctx.IDENTIFIER() != null) {
            return environment.findVariable(ctx.IDENTIFIER().getText());
        }
        if (ctx.NUMBER() != null) {
            return new Value(Double.parseDouble(ctx.NUMBER().getText()));
        }
        if (ctx.STRING() != null) {
            return Value.fromPrimaryString(ctx.STRING().getText());
        }
        if (ctx.getText().equals("true")) {
            return Value.TRUE;
        }
        return Value.FALSE;
    }
}