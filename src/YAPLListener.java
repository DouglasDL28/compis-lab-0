// Generated from YAPL.g4 by ANTLR 4.10.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link YAPLParser}.
 */
public interface YAPLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link YAPLParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(YAPLParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link YAPLParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(YAPLParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link YAPLParser#classDef}.
	 * @param ctx the parse tree
	 */
	void enterClassDef(YAPLParser.ClassDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link YAPLParser#classDef}.
	 * @param ctx the parse tree
	 */
	void exitClassDef(YAPLParser.ClassDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funcDef}
	 * labeled alternative in {@link YAPLParser#feature}.
	 * @param ctx the parse tree
	 */
	void enterFuncDef(YAPLParser.FuncDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funcDef}
	 * labeled alternative in {@link YAPLParser#feature}.
	 * @param ctx the parse tree
	 */
	void exitFuncDef(YAPLParser.FuncDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code attrDef}
	 * labeled alternative in {@link YAPLParser#feature}.
	 * @param ctx the parse tree
	 */
	void enterAttrDef(YAPLParser.AttrDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code attrDef}
	 * labeled alternative in {@link YAPLParser#feature}.
	 * @param ctx the parse tree
	 */
	void exitAttrDef(YAPLParser.AttrDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link YAPLParser#formal}.
	 * @param ctx the parse tree
	 */
	void enterFormal(YAPLParser.FormalContext ctx);
	/**
	 * Exit a parse tree produced by {@link YAPLParser#formal}.
	 * @param ctx the parse tree
	 */
	void exitFormal(YAPLParser.FormalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code new}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNew(YAPLParser.NewContext ctx);
	/**
	 * Exit a parse tree produced by the {@code new}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNew(YAPLParser.NewContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parens}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParens(YAPLParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParens(YAPLParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code string}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterString(YAPLParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code string}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitString(YAPLParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMulDiv(YAPLParser.MulDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMulDiv(YAPLParser.MulDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddSub(YAPLParser.AddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddSub(YAPLParser.AddSubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code isvoid}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIsvoid(YAPLParser.IsvoidContext ctx);
	/**
	 * Exit a parse tree produced by the {@code isvoid}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIsvoid(YAPLParser.IsvoidContext ctx);
	/**
	 * Enter a parse tree produced by the {@code false}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFalse(YAPLParser.FalseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code false}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFalse(YAPLParser.FalseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code integer}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterInteger(YAPLParser.IntegerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code integer}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitInteger(YAPLParser.IntegerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code while}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterWhile(YAPLParser.WhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code while}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitWhile(YAPLParser.WhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code declaration}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(YAPLParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code declaration}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(YAPLParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code brackets}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBrackets(YAPLParser.BracketsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code brackets}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBrackets(YAPLParser.BracketsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Comp}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterComp(YAPLParser.CompContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Comp}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitComp(YAPLParser.CompContext ctx);
	/**
	 * Enter a parse tree produced by the {@code not}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNot(YAPLParser.NotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code not}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNot(YAPLParser.NotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code true}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterTrue(YAPLParser.TrueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code true}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitTrue(YAPLParser.TrueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code self}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSelf(YAPLParser.SelfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code self}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSelf(YAPLParser.SelfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code let}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLet(YAPLParser.LetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code let}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLet(YAPLParser.LetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code id}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterId(YAPLParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code id}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitId(YAPLParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funcCall}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFuncCall(YAPLParser.FuncCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funcCall}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFuncCall(YAPLParser.FuncCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifElse}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIfElse(YAPLParser.IfElseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifElse}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIfElse(YAPLParser.IfElseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assign}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAssign(YAPLParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assign}
	 * labeled alternative in {@link YAPLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAssign(YAPLParser.AssignContext ctx);
}