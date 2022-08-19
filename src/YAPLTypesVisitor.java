public class YAPLTypesVisitor extends YAPLBaseVisitor<Void> {
    private YAPLTypesTable types;

    public YAPLTypesVisitor(YAPLTypesTable types) {
        this.types = types;
    }

    @Override
    public Void visitProgram(YAPLParser.ProgramContext ctx) {
        return super.visitProgram(ctx);
    }

    @Override
    public Void visitClassDef(YAPLParser.ClassDefContext ctx) {
        return super.visitClassDef(ctx);
    }

    @Override
    public Void visitFuncDef(YAPLParser.FuncDefContext ctx) {
        return super.visitFuncDef(ctx);
    }

    @Override
    public Void visitVarDef(YAPLParser.VarDefContext ctx) {
        return super.visitVarDef(ctx);
    }

    @Override
    public Void visitFormal(YAPLParser.FormalContext ctx) {
        return super.visitFormal(ctx);
    }

    @Override
    public Void visitNew(YAPLParser.NewContext ctx) {
        return super.visitNew(ctx);
    }

    @Override
    public Void visitParens(YAPLParser.ParensContext ctx) {
        return super.visitParens(ctx);
    }

    @Override
    public Void visitString(YAPLParser.StringContext ctx) {
        return super.visitString(ctx);
    }

    @Override
    public Void visitMulDiv(YAPLParser.MulDivContext ctx) {
        return super.visitMulDiv(ctx);
    }

    @Override
    public Void visitAddSub(YAPLParser.AddSubContext ctx) {
        return super.visitAddSub(ctx);
    }

    @Override
    public Void visitIsvoid(YAPLParser.IsvoidContext ctx) {
        return super.visitIsvoid(ctx);
    }

    @Override
    public Void visitFalse(YAPLParser.FalseContext ctx) {
        return super.visitFalse(ctx);
    }

    @Override
    public Void visitInteger(YAPLParser.IntegerContext ctx) {
        return super.visitInteger(ctx);
    }

    @Override
    public Void visitWhile(YAPLParser.WhileContext ctx) {
        return super.visitWhile(ctx);
    }

    @Override
    public Void visitDeclaration(YAPLParser.DeclarationContext ctx) {
        return super.visitDeclaration(ctx);
    }

    @Override
    public Void visitBrackets(YAPLParser.BracketsContext ctx) {
        return super.visitBrackets(ctx);
    }

    @Override
    public Void visitComp(YAPLParser.CompContext ctx) {
        return super.visitComp(ctx);
    }

    @Override
    public Void visitNot(YAPLParser.NotContext ctx) {
        return super.visitNot(ctx);
    }

    @Override
    public Void visitTrue(YAPLParser.TrueContext ctx) {
        return super.visitTrue(ctx);
    }

    @Override
    public Void visitSelf(YAPLParser.SelfContext ctx) {
        return super.visitSelf(ctx);
    }

    @Override
    public Void visitLet(YAPLParser.LetContext ctx) {
        return super.visitLet(ctx);
    }

    @Override
    public Void visitId(YAPLParser.IdContext ctx) {
        return super.visitId(ctx);
    }

    @Override
    public Void visitFuncCall(YAPLParser.FuncCallContext ctx) {
        return super.visitFuncCall(ctx);
    }

    @Override
    public Void visitIfElse(YAPLParser.IfElseContext ctx) {
        return super.visitIfElse(ctx);
    }

    @Override
    public Void visitAssign(YAPLParser.AssignContext ctx) {
        return super.visitAssign(ctx);
    }
}
