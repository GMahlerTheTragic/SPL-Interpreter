package interpreter.visitor;

// Generated from SPL.g4 by ANTLR 4.13.0
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

import frontend.SPLParser;
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
import interpreter.Value;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SPLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface SPLVisitor extends ParseTreeVisitor<Value> {
	/**
	 * Visit a parse tree produced by {@link SPLParser#program}.
	 * 
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	void visitProgram(ProgramContext ctx);

	/**
	 * Visit a parse tree produced by {@link SPLParser#declaration}.
	 * 
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	void visitDeclaration(DeclarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link SPLParser#varDecl}.
	 * 
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	void visitVarDecl(VarDeclContext ctx);

	/**
	 * Visit a parse tree produced by {@link SPLParser#statement}.
	 * 
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	void visitStatement(StatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link SPLParser#exprStmt}.
	 * 
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Value visitExprStmt(ExprStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link SPLParser#ifStmt}.
	 * 
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	void visitIfStmt(IfStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link SPLParser#printStmt}.
	 * 
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	void visitPrintStmt(PrintStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link SPLParser#whileStmt}.
	 * 
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	void visitWhileStmt(WhileStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link SPLParser#block}.
	 * 
	 * @param ctx the parse tree
	 * @return the visitor result
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
	Value visitLogic_or(Logic_orContext ctx);

	/**
	 * Visit a parse tree produced by {@link SPLParser#logic_and}.
	 * 
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Value visitLogic_and(Logic_andContext ctx);

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