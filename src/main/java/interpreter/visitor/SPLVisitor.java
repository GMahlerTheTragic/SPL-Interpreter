package interpreter.visitor;


// Generated from SPL.g4 by ANTLR 4.13.0

import frontend.SPLParser;
import frontend.SPLParser.*;
import interpreter.Value;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SPLParser}.
 */
public interface SPLVisitor extends ParseTreeVisitor<Value> {
    /**
     * Visit a parse tree produced by {@link SPLParser#program}.
     *
     * @param ctx the parse tree
     */
    void visitProgram(ProgramContext ctx);

    /**
     * Visit a parse tree produced by {@link SPLParser#declaration}.
     *
     * @param ctx the parse tree
     */
    void visitDeclaration(DeclarationContext ctx);

    /**
     * Visit a parse tree produced by {@link SPLParser#varDecl}.
     *
     * @param ctx the parse tree
     */
    void visitVarDecl(VarDeclContext ctx);

    /**
     * Visit a parse tree produced by {@link SPLParser#statement}.
     *
     * @param ctx the parse tree
     */
    void visitStatement(StatementContext ctx);

    /**
     * Visit a parse tree produced by {@link SPLParser#exprStmt}.
     *
     * @param ctx the parse tree
     */
    Value visitExprStmt(ExprStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link SPLParser#ifStmt}.
     *
     * @param ctx the parse tree
     */
    void visitIfStmt(IfStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link SPLParser#printStmt}.
     *
     * @param ctx the parse tree
     */
    void visitPrintStmt(PrintStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link SPLParser#whileStmt}.
     *
     * @param ctx the parse tree
     */
    void visitWhileStmt(WhileStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link SPLParser#block}.
     *
     * @param ctx the parse tree
     */
    void visitBlock(BlockContext ctx);

    /**
     * Visit a parse tree produced by {@link SPLParser#expression}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    Value visitExpression(ExpressionContext ctx);

    /**
     * Visit a parse tree produced by {@link SPLParser#assignment}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    Value visitAssignment(AssignmentContext ctx);

    /**
     * Visit a parse tree produced by {@link SPLParser#logic_or}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    Value visitLogicOr(Logic_orContext ctx);

    /**
     * Visit a parse tree produced by {@link SPLParser#logic_and}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    Value visitLogicAnd(Logic_andContext ctx);

    /**
     * Visit a parse tree produced by {@link SPLParser#equality}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    Value visitEquality(EqualityContext ctx);

    /**
     * Visit a parse tree produced by {@link SPLParser#comparison}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    Value visitComparison(ComparisonContext ctx);

    /**
     * Visit a parse tree produced by {@link SPLParser#term}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    Value visitTerm(TermContext ctx);

    /**
     * Visit a parse tree produced by {@link SPLParser#factor}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    Value visitFactor(FactorContext ctx);

    /**
     * Visit a parse tree produced by {@link SPLParser#unary}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    Value visitUnary(UnaryContext ctx);

    /**
     * Visit a parse tree produced by {@link SPLParser#primary}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    Value visitPrimary(PrimaryContext ctx);
}