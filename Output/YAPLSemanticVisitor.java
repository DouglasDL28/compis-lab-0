public class YAPLSemanticVisitor extends YAPLBaseVisitor<YAPLType> {

    private YAPLScopesStack scopes = new YAPLScopesStack();


    /**
     * Tree root.
     * @param ctx the parse tree
     * @return
     */
    @Override
    public YAPLType visitProgram(YAPLParser.ProgramContext ctx) {
        return super.visitProgram(ctx);
    }
    /**
     * `left op=('+'|'-') right`
     *
     * if left.type == right.type return right.type else SemError
     *
     * @param ctx the parse tree
     * @return YAPlType
     */

    /**
     * @param ctx the parse tree
     * @return
     */
    @Override
    public YAPLType visitClassDef(YAPLParser.ClassDefContext ctx) {
        return super.visitClassDef(ctx);
    }

    /**
     * @param ctx the parse tree
     * @return
     */
    @Override
    public YAPLType visitFuncDef(YAPLParser.FuncDefContext ctx) {
        return super.visitFuncDef(ctx);
    }

    /**
     * @param ctx the parse tree
     * @return
     */
    @Override
    public YAPLType visitAttrDef(YAPLParser.AttrDefContext ctx) {
        return super.visitAttrDef(ctx);
    }

    /**
     * @param ctx the parse tree
     * @return
     */
    @Override
    public YAPLType visitFormal(YAPLParser.FormalContext ctx) {
        return super.visitFormal(ctx);
    }

    @Override
    public YAPLType visitMulDiv(YAPLParser.MulDivContext ctx) {
        YAPLType left = visit(ctx.expr(0)); // left expr
        YAPLType right = visit(ctx.expr(1)); // right expr

        if (left.getId().equals("SemError") || right.getId().equals("SemError")) {
            System.out.println("SemError");
            return new YAPLType("SemError");
        }

        if (left.equals(right)) {
            System.out.println(right.getId());
            return right;
        }

        return new YAPLType("SemError");
    }

    /**
     * `left op=('+'|'-') right`
     *
     * if left.type == right.type return right.type else SemError
     *
     * @param ctx the parse tree
     * @return YAPLType
     */
    @Override
    public YAPLType visitAddSub(YAPLParser.AddSubContext ctx) {
        YAPLType left = visit(ctx.expr(0)); // left expr
        YAPLType right = visit(ctx.expr(1)); // right expr
        System.out.println("visitAddSub");

        if (left.getId().equals("SemError") || right.getId().equals("SemError")) {
            System.out.println("SemError");
            return new YAPLType("SemError");
        }

        if (left.equals(right)) {
            System.out.println(right.getId());
            return right;
        }

        return new YAPLType("SemError");
    }

    /**
     * `self`
     *
     * @param ctx the parse tree
     * @return SELF_TYPE
     */
    @Override
    public YAPLType visitSelf(YAPLParser.SelfContext ctx) {
        System.out.println("visitSelf");
        return new YAPLType("SELF_TYPE");
    }

    /**
     * `false`
     * @param ctx the parse tree
     * @return Bool
     */
    @Override
    public YAPLType visitFalse(YAPLParser.FalseContext ctx) {
        System.out.println("visitFalse");
        return new YAPLType("Bool");
    }

    /**
     * `true`
     * @param ctx the parse tree
     * @return Bool
     */
    @Override
    public YAPLType visitTrue(YAPLParser.TrueContext ctx) {
        System.out.println("visitTrue");
        return new YAPLType("Bool");
    }

    /**
     * '"' (~(EOF| '"') | '\\')*  '"'
     * @param ctx the parse tree
     * @return String
     */
    @Override
    public YAPLType visitString(YAPLParser.StringContext ctx) {
        System.out.println("visitString");
        return new YAPLType("String");
    }


    // Acá empieza lo generado por IntelliJ


    /**
     * NEW TYPE
     * @param ctx the parse tree
     * @return Returns type.
     */
    @Override
    public YAPLType visitNew(YAPLParser.NewContext ctx) {
        System.out.println("visitNew");
        String type = ctx.TYPE().toString();
        System.out.println(type);
        return new YAPLType(type);
    }

    /**
     * '(' expr ')'
     * Returns expr type.
     * @param ctx the parse tree
     * @return expr type
     */
    @Override
    public YAPLType visitParens(YAPLParser.ParensContext ctx) {
        System.out.println("visitParens");
        return visit(ctx.expr());
    }

    /**
     * ISVOID expr
     * @param ctx the parse tree
     * @return Bool
     */
    @Override
    public YAPLType visitIsvoid(YAPLParser.IsvoidContext ctx) {
        System.out.println("visitIsvoid");

        return new YAPLType("Bool");
    }

    /**
     * INT
     * @param ctx the parse tree
     * @return Int
     */
    @Override
    public YAPLType visitInteger(YAPLParser.IntegerContext ctx) {
        System.out.println("visitInteger");
        return new YAPLType("Int");
    }

    /**
     * TODO: No recuerdo cómo era esto
     * WHILE expr LOOP expr POOL
     * @param ctx the parse tree
     * @return
     */
    @Override
    public YAPLType visitWhile(YAPLParser.WhileContext ctx) {
        return super.visitWhile(ctx);
    }

    /**
     * @param ctx the parse tree
     * @return
     */
    @Override
    public YAPLType visitDeclaration(YAPLParser.DeclarationContext ctx) {
        return super.visitDeclaration(ctx);
    }

    /**
     * @param ctx the parse tree
     * @return
     */
    @Override
    public YAPLType visitBrackets(YAPLParser.BracketsContext ctx) {
        return super.visitBrackets(ctx);
    }

    /**
     * @param ctx the parse tree
     * @return
     */
    @Override
    public YAPLType visitComp(YAPLParser.CompContext ctx) {
        return super.visitComp(ctx);
    }

    /**
     * @param ctx the parse tree
     * @return
     */
    @Override
    public YAPLType visitNot(YAPLParser.NotContext ctx) {
        return super.visitNot(ctx);
    }

    /**
     * @param ctx the parse tree
     * @return
     */
    @Override
    public YAPLType visitLet(YAPLParser.LetContext ctx) {
        return super.visitLet(ctx);
    }

    /**
     * @param ctx the parse tree
     * @return
     */
    @Override
    public YAPLType visitId(YAPLParser.IdContext ctx) {
        return super.visitId(ctx);
    }

    /**
     * @param ctx the parse tree
     * @return
     */
    @Override
    public YAPLType visitFuncCall(YAPLParser.FuncCallContext ctx) {
        return super.visitFuncCall(ctx);
    }

    /**
     * @param ctx the parse tree
     * @return
     */
    @Override
    public YAPLType visitIfElse(YAPLParser.IfElseContext ctx) {
        return super.visitIfElse(ctx);
    }

    /**
     * @param ctx the parse tree
     * @return
     */
    @Override
    public YAPLType visitAssign(YAPLParser.AssignContext ctx) {
        return super.visitAssign(ctx);
    }
}
