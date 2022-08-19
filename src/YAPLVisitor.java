// Generated from YAPL.g4 by ANTLR 4.10.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link YAPLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface YAPLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link YAPLParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(YAPLParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link YAPLParser#classDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDef(YAPLParser.ClassDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link YAPLParser#funcDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDef(YAPLParser.FuncDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link YAPLParser#varDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDef(YAPLParser.VarDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link YAPLParser#formal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormal(YAPLParser.FormalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code new}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNew(YAPLParser.NewContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(YAPLParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code string}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(YAPLParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDiv(YAPLParser.MulDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSub(YAPLParser.AddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code isvoid}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsvoid(YAPLParser.IsvoidContext ctx);
	/**
	 * Visit a parse tree produced by the {@code false}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalse(YAPLParser.FalseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code integer}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger(YAPLParser.IntegerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code while}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile(YAPLParser.WhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code declaration}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(YAPLParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code brackets}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBrackets(YAPLParser.BracketsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Comp}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp(YAPLParser.CompContext ctx);
	/**
	 * Visit a parse tree produced by the {@code not}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNot(YAPLParser.NotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code true}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrue(YAPLParser.TrueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code self}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelf(YAPLParser.SelfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code let}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLet(YAPLParser.LetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code id}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(YAPLParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funcCall}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCall(YAPLParser.FuncCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifElse}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfElse(YAPLParser.IfElseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assign}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(YAPLParser.AssignContext ctx);
}