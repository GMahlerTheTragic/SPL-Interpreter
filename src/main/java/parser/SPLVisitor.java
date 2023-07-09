package parser;
// Generated from SPL.g4 by ANTLR 4.13.0
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SPLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SPLVisitor extends ParseTreeVisitor<Value> {
	/**
	 * Visit a parse tree produced by {@link SPLParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	void visitProgram(SPLParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPLParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	void visitDeclaration(SPLParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPLParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	void visitVarDecl(SPLParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	void visitStatement(SPLParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPLParser#exprStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Value visitExprStmt(SPLParser.ExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPLParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	void visitIfStmt(SPLParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPLParser#printStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	void visitPrintStmt(SPLParser.PrintStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPLParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	void visitWhileStmt(SPLParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPLParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	void visitBlock(SPLParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Value visitExpression(SPLParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPLParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Value visitAssignment(SPLParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPLParser#logic_or}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Value visitLogic_or(SPLParser.Logic_orContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPLParser#logic_and}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Value visitLogic_and(SPLParser.Logic_andContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPLParser#equality}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Value visitEquality(SPLParser.EqualityContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPLParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Value visitComparison(SPLParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPLParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Value visitTerm(SPLParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPLParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Value visitFactor(SPLParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPLParser#unary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Value visitUnary(SPLParser.UnaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPLParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Value visitPrimary(SPLParser.PrimaryContext ctx);
}