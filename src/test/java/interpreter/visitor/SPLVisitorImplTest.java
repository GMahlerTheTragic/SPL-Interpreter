package interpreter.visitor;

import org.mockito.Mockito;

import frontend.SPLLexer;
import frontend.SPLParser.AssignmentContext;
import frontend.SPLParser.BlockContext;
import frontend.SPLParser.ComparisonContext;
import frontend.SPLParser.DeclarationContext;
import frontend.SPLParser.EqualityContext;
import frontend.SPLParser.ExprStmtContext;
import frontend.SPLParser.ExpressionContext;
import frontend.SPLParser.FactorContext;
import frontend.SPLParser.IfStmtContext;
import frontend.SPLParser.Logic_andContext;
import frontend.SPLParser.Logic_orContext;
import frontend.SPLParser.PrimaryContext;
import frontend.SPLParser.PrintStmtContext;
import frontend.SPLParser.ProgramContext;
import frontend.SPLParser.StatementContext;
import frontend.SPLParser.TermContext;
import frontend.SPLParser.UnaryContext;
import frontend.SPLParser.VarDeclContext;
import frontend.SPLParser.WhileStmtContext;
import interpreter.Environment;
import interpreter.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.jupiter.api.Test;

public class SPLVisitorImplTest {

    private SPLLexer lexer = new SPLLexer(CharStreams.fromString("asdf"));
    private CommonTokenStream tokens = new CommonTokenStream(lexer);
    private Environment environment = Mockito.mock(Environment.class);
    private SPLVisitorImpl visitor = new SPLVisitorImpl(tokens, environment);

    @Test
    void testVisitProgram() {
        ProgramContext programContext = Mockito.mock(ProgramContext.class);
        DeclarationContext declarationContext1 = Mockito.mock(DeclarationContext.class);
        DeclarationContext declarationContext2 = Mockito.mock(DeclarationContext.class);
        when(programContext.declaration()).thenReturn(Arrays.asList(declarationContext1, declarationContext2));
        SPLVisitorImpl visitorSpy = spy(visitor);
        doNothing().when(visitorSpy).visitDeclaration(any());
        visitorSpy.visitProgram(programContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitProgram(programContext);
        verify(visitorSpy).visitDeclaration(declarationContext1);
        verify(visitorSpy).visitDeclaration(declarationContext2);
        verifyNoMoreInteractions(visitorSpy);
    }

    @Test
    void testVisitDeclaration() {
        DeclarationContext declarationContext = Mockito.mock(DeclarationContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        VarDeclContext varDeclContest = Mockito.mock(VarDeclContext.class);
        StatementContext statement = Mockito.mock(StatementContext.class);
        doReturn(varDeclContest).when(declarationContext).varDecl();
        doReturn(statement).when(declarationContext).statement();
        doNothing().when(visitorSpy).visitVarDecl(varDeclContest);
        doNothing().when(visitorSpy).visitStatement(statement);
        visitorSpy.visitDeclaration(declarationContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitDeclaration(declarationContext);
        verify(visitorSpy).visitVarDecl(varDeclContest);
        verify(visitorSpy).visitStatement(statement);
        verifyNoMoreInteractions(visitorSpy);
    }

    @Test
    void testVisitVarDecl() {
        VarDeclContext varDeclContext = Mockito.mock(VarDeclContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        TerminalNode node = Mockito.mock(TerminalNode.class);
        ExpressionContext expressionContext = Mockito.mock(ExpressionContext.class);
        when(varDeclContext.IDENTIFIER()).thenReturn(node);
        when(varDeclContext.expression()).thenReturn(expressionContext);

        Value value = new Value(42);
        doReturn("test").when(node).getText();
        doReturn(value).when(visitorSpy).visitExpression(expressionContext);
        doNothing().when(environment).assignVariable("test", value);
        visitorSpy.visitVarDecl(varDeclContext);

        // Verify the expected method invocations
        verify(environment).assignVariable("test", value);
        verify(node).getText();
        verify(visitorSpy).visitVarDecl(varDeclContext);
        verify(visitorSpy).visitExpression(expressionContext);
        verifyNoMoreInteractions(visitorSpy);
    }

    @Test
    void testVisitStatement() {
        StatementContext statementContext = Mockito.mock(StatementContext.class);
        ExprStmtContext exprStmtContext = Mockito.mock(ExprStmtContext.class);
        PrintStmtContext printStmtContext = Mockito.mock(PrintStmtContext.class);
        IfStmtContext ifStmtContext = Mockito.mock(IfStmtContext.class);
        WhileStmtContext whileStmtContext = Mockito.mock(WhileStmtContext.class);
        BlockContext blockContext = Mockito.mock(BlockContext.class);

        doReturn(exprStmtContext).when(statementContext).exprStmt();
        doReturn(printStmtContext).when(statementContext).printStmt();
        doReturn(ifStmtContext).when(statementContext).ifStmt();
        doReturn(whileStmtContext).when(statementContext).whileStmt();
        doReturn(blockContext).when(statementContext).block();

        SPLVisitorImpl visitorSpy = spy(visitor);
        Value value = new Value(42);
        doReturn(value).when(visitorSpy).visitExprStmt(any());
        doNothing().when(visitorSpy).visitPrintStmt(any());
        doNothing().when(visitorSpy).visitIfStmt(any());
        doNothing().when(visitorSpy).visitWhileStmt(any());
        doNothing().when(visitorSpy).visitBlock(any());

        visitorSpy.visitStatement(statementContext);

        verify(visitorSpy).visitStatement(statementContext);
        verify(visitorSpy).visitExprStmt(any());
        verify(visitorSpy).visitPrintStmt(any());
        verify(visitorSpy).visitIfStmt(any());
        verify(visitorSpy).visitWhileStmt(any());
        verify(visitorSpy).visitBlock(any());
        verifyNoMoreInteractions(visitorSpy);
    }

    @Test
    void testVisitExprStmt() {
        ExprStmtContext exprStmtContext = Mockito.mock(ExprStmtContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        when(exprStmtContext.expression()).thenReturn(Mockito.mock(ExpressionContext.class));
        doReturn(null).when(visitorSpy).visitExpression(any());
        visitorSpy.visitExprStmt(exprStmtContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitExprStmt(exprStmtContext);
        verify(visitorSpy).visitExpression(any());
        verifyNoMoreInteractions(visitorSpy);
    }

    @Test
    void testVisitIfStmtTrue() {
        IfStmtContext ifStmtContext = Mockito.mock(IfStmtContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        ExpressionContext expressionContext = Mockito.mock(ExpressionContext.class);
        StatementContext statementContext1 = Mockito.mock(StatementContext.class);
        StatementContext statementContext2 = Mockito.mock(StatementContext.class);

        when(ifStmtContext.expression()).thenReturn(expressionContext);
        doReturn(Value.TRUE).when(visitorSpy).visitExpression(expressionContext);
        when(ifStmtContext.statement(0)).thenReturn(statementContext1);
        when(ifStmtContext.statement(1)).thenReturn(statementContext2);
        doNothing().when(visitorSpy).visitStatement(any());

        visitorSpy.visitIfStmt(ifStmtContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitIfStmt(ifStmtContext);
        verify(visitorSpy).visitExpression(expressionContext);
        verify(visitorSpy).visitStatement(statementContext1);
        verifyNoMoreInteractions(visitorSpy);
    }

    @Test
    void testVisitIfStmtFalse() {
        IfStmtContext ifStmtContext = Mockito.mock(IfStmtContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        ExpressionContext expressionContext = Mockito.mock(ExpressionContext.class);
        StatementContext statementContext1 = Mockito.mock(StatementContext.class);
        StatementContext statementContext2 = Mockito.mock(StatementContext.class);

        when(ifStmtContext.expression()).thenReturn(expressionContext);
        doReturn(Value.FALSE).when(visitorSpy).visitExpression(expressionContext);
        when(ifStmtContext.statement(0)).thenReturn(statementContext1);
        when(ifStmtContext.statement(1)).thenReturn(statementContext2);
        doNothing().when(visitorSpy).visitStatement(any());

        visitorSpy.visitIfStmt(ifStmtContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitIfStmt(ifStmtContext);
        verify(visitorSpy).visitExpression(expressionContext);
        verify(visitorSpy).visitStatement(statementContext2);
        verifyNoMoreInteractions(visitorSpy);
    }

    @Test
    void testVisitPrintStmt() {
        PrintStmtContext printStmtContext = Mockito.mock(PrintStmtContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        when(printStmtContext.expression()).thenReturn(Mockito.mock(ExpressionContext.class));
        doReturn(null).when(visitorSpy).visitExpression(any());
        visitorSpy.visitPrintStmt(printStmtContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitPrintStmt(printStmtContext);
        verify(visitorSpy).visitExpression(any());
        verifyNoMoreInteractions(visitorSpy);
    }

    @Test
    void testVisitWhileStmtNoIteration() {
        WhileStmtContext whileStmtContext = Mockito.mock(WhileStmtContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        ExpressionContext expressionContext = Mockito.mock(ExpressionContext.class);
        StatementContext statementContext = Mockito.mock(StatementContext.class);

        when(whileStmtContext.expression()).thenReturn(expressionContext);
        doReturn(Value.FALSE).when(visitorSpy).visitExpression(expressionContext);
        when(whileStmtContext.statement()).thenReturn(statementContext);
        doNothing().when(visitorSpy).visitStatement(any());

        visitorSpy.visitWhileStmt(whileStmtContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitWhileStmt(whileStmtContext);
        verify(visitorSpy).visitExpression(expressionContext);
        verifyNoMoreInteractions(visitorSpy);
    }

    @Test
    void testVisitWhileStmtOneIteration() {
        WhileStmtContext whileStmtContext = Mockito.mock(WhileStmtContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        ExpressionContext expressionContext = Mockito.mock(ExpressionContext.class);
        StatementContext statementContext = Mockito.mock(StatementContext.class);

        when(whileStmtContext.expression()).thenReturn(expressionContext);
        doReturn(Value.TRUE, Value.FALSE).when(visitorSpy).visitExpression(expressionContext);
        when(whileStmtContext.statement()).thenReturn(statementContext);
        doNothing().when(visitorSpy).visitStatement(any());

        visitorSpy.visitWhileStmt(whileStmtContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitWhileStmt(whileStmtContext);
        verify(visitorSpy, times(2)).visitExpression(expressionContext);
        verify(visitorSpy).visitStatement(statementContext);
        verifyNoMoreInteractions(visitorSpy);
    }

    @Test
    void testVisitWhileStmtTwoIteration() {
        WhileStmtContext whileStmtContext = Mockito.mock(WhileStmtContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        ExpressionContext expressionContext = Mockito.mock(ExpressionContext.class);
        StatementContext statementContext = Mockito.mock(StatementContext.class);

        when(whileStmtContext.expression()).thenReturn(expressionContext);
        doReturn(Value.TRUE, Value.TRUE, Value.FALSE).when(visitorSpy).visitExpression(expressionContext);
        when(whileStmtContext.statement()).thenReturn(statementContext);
        doNothing().when(visitorSpy).visitStatement(any());

        visitorSpy.visitWhileStmt(whileStmtContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitWhileStmt(whileStmtContext);
        verify(visitorSpy, times(3)).visitExpression(expressionContext);
        verify(visitorSpy, times(2)).visitStatement(statementContext);
        verifyNoMoreInteractions(visitorSpy);
    }

    @Test
    void testVisitBlock() {
        BlockContext blockContext = Mockito.mock(BlockContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        when(blockContext.declaration()).thenReturn(Arrays.asList(Mockito.mock(DeclarationContext.class)));
        doNothing().when(visitorSpy).visitDeclaration(any());
        visitorSpy.visitBlock(blockContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitBlock(blockContext);
        verify(visitorSpy).visitDeclaration(any());
        verifyNoMoreInteractions(visitorSpy);
    }

    @Test
    void testVisitExpression() {
        ExpressionContext expressionContext = Mockito.mock(ExpressionContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        when(expressionContext.assignment()).thenReturn(Mockito.mock(AssignmentContext.class));
        doReturn(null).when(visitorSpy).visitAssignment(any());
        visitorSpy.visitExpression(expressionContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitExpression(expressionContext);
        verify(visitorSpy).visitAssignment(any());
        verifyNoMoreInteractions(visitorSpy);
    }

    @Test
    void testVisitAssignmentWithIdentifier() {
        AssignmentContext assignmentContext = Mockito.mock(AssignmentContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        TerminalNode identifier = Mockito.mock(TerminalNode.class);
        AssignmentContext assignmentContext1 = Mockito.mock(AssignmentContext.class);
        Logic_orContext logicOrContext = Mockito.mock(Logic_orContext.class);
        when(assignmentContext.IDENTIFIER()).thenReturn(identifier);
        when(assignmentContext.assignment()).thenReturn(assignmentContext1);
        when(assignmentContext.logic_or()).thenReturn(logicOrContext);
        when(identifier.getText()).thenReturn("test");
        Value value = new Value("22");
        doReturn(value).when(visitorSpy).visitAssignment(assignmentContext1);
        visitorSpy.visitAssignment(assignmentContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitAssignment(assignmentContext);

        verify(assignmentContext, times(2)).IDENTIFIER();
        verify(visitorSpy).visitAssignment(assignmentContext1);
        verify(environment).assignVariable("test", value);
        verifyNoMoreInteractions(visitorSpy);
        verifyNoMoreInteractions(environment);
    }

    @Test
    void testVisitAssignmentWithoutIdentifier() {
        AssignmentContext assignmentContext = Mockito.mock(AssignmentContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        Logic_orContext logicOrContext = Mockito.mock(Logic_orContext.class);
        when(assignmentContext.logic_or()).thenReturn(logicOrContext);
        Value value = new Value("22");
        doReturn(value).when(visitorSpy).visitLogic_or(logicOrContext);
        Value result = visitorSpy.visitAssignment(assignmentContext);
        assertEquals(value, result);
        // Verify the expected method invocations
        verify(visitorSpy).visitLogic_or(logicOrContext);
    }

    // ToDo
    @Test
    void testVisitLogic_or() {
        Logic_orContext logicOrContext = Mockito.mock(Logic_orContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        when(logicOrContext.logic_and()).thenReturn(Arrays.asList(Mockito.mock(Logic_andContext.class)));
        doReturn(Value.FALSE).when(visitorSpy).visitLogic_and(any());
        visitorSpy.visitLogic_or(logicOrContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitLogic_or(logicOrContext);
        verify(visitorSpy).visitLogic_and(any());
        verifyNoMoreInteractions(visitorSpy);
    }

    // ToDo
    @Test
    void testVisitLogic_and() {
        Logic_andContext logicAndContext = Mockito.mock(Logic_andContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        when(logicAndContext.equality()).thenReturn(Arrays.asList(Mockito.mock(EqualityContext.class)));
        doReturn(Value.TRUE).when(visitorSpy).visitEquality(any());
        visitorSpy.visitLogic_and(logicAndContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitLogic_and(logicAndContext);
        verify(visitorSpy).visitEquality(any());
        verifyNoMoreInteractions(visitorSpy);
    }

    // ToDo
    @Test
    void testVisitEquality() {
        EqualityContext equalityContext = Mockito.mock(EqualityContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        when(equalityContext.comparison()).thenReturn(Arrays.asList(Mockito.mock(ComparisonContext.class)));
        doReturn(Value.TRUE).when(visitorSpy).visitComparison(any());
        visitorSpy.visitEquality(equalityContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitEquality(equalityContext);
        verify(visitorSpy).visitComparison(any());
        verifyNoMoreInteractions(visitorSpy);
    }

    // ToDo
    @Test
    void testVisitComparison() {
        ComparisonContext comparisonContext = Mockito.mock(ComparisonContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        when(comparisonContext.term()).thenReturn(Arrays.asList(Mockito.mock(TermContext.class)));
        doReturn(Value.TRUE).when(visitorSpy).visitTerm(any());
        visitorSpy.visitComparison(comparisonContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitComparison(comparisonContext);
        verify(visitorSpy).visitTerm(any());
        verifyNoMoreInteractions(visitorSpy);
    }

    // ToDo
    @Test
    void testVisitTerm() {
        TermContext termContext = Mockito.mock(TermContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        when(termContext.factor()).thenReturn(Arrays.asList(Mockito.mock(FactorContext.class)));
        doReturn(new Value(42)).when(visitorSpy).visitFactor(any());
        visitorSpy.visitTerm(termContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitTerm(termContext);
        verify(visitorSpy).visitFactor(any());
        verifyNoMoreInteractions(visitorSpy);
    }

    // ToDo
    @Test
    void testVisitFactor() {
        FactorContext factorContext = Mockito.mock(FactorContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        when(factorContext.unary()).thenReturn(Arrays.asList(Mockito.mock(UnaryContext.class)));
        doReturn(new Value(42)).when(visitorSpy).visitUnary(any());
        visitorSpy.visitFactor(factorContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitFactor(factorContext);
        verify(visitorSpy).visitUnary(any());
        verifyNoMoreInteractions(visitorSpy);
    }

    // ToDo
    @Test
    void testVisitUnary() {
        UnaryContext unaryContext = Mockito.mock(UnaryContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        when(unaryContext.primary()).thenReturn(Mockito.mock(PrimaryContext.class));
        when(unaryContext.unary()).thenReturn(Mockito.mock(UnaryContext.class));
        doReturn(new Value(42)).when(visitorSpy).visitPrimary(any());
        visitorSpy.visitUnary(unaryContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitUnary(unaryContext);
        verify(visitorSpy).visitPrimary(any());
        verifyNoMoreInteractions(visitorSpy);
    }

    // ToDo
    @Test
    void testVisitPrimary() {
        PrimaryContext primaryContext = Mockito.mock(PrimaryContext.class);
        SPLVisitorImpl visitorSpy = spy(visitor);
        when(primaryContext.expression()).thenReturn(Mockito.mock(ExpressionContext.class));
        when(primaryContext.IDENTIFIER()).thenReturn(Mockito.mock(TerminalNode.class));
        when(primaryContext.NUMBER()).thenReturn(Mockito.mock(TerminalNode.class));
        when(primaryContext.STRING()).thenReturn(Mockito.mock(TerminalNode.class));
        doReturn(new Value(42)).when(visitorSpy).visitExpression(any());
        visitorSpy.visitPrimary(primaryContext);

        // Verify the expected method invocations
        verify(visitorSpy).visitPrimary(primaryContext);
        verify(visitorSpy).visitExpression(any());
        verifyNoMoreInteractions(visitorSpy);
    }
}
