import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class YAPLSemanticVisitor extends YAPLBaseVisitor<YAPLType> {

    private YAPLTypesTable types = new YAPLTypesTable();
//    private YAPLScopesStack scopes = new YAPLScopesStack();

    private Stack<YAPLSymbolTable> scopes = new Stack<>();

    private YAPLType currentClass = null;


    /**
     * Tree root.
     * @param ctx the parse tree
     * @return TODO
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
     * TODO: que valor tiene?
     * TODO: cómo agregar los features?
     * @param ctx the parse tree
     * @return TODO
     */
    @Override
    public YAPLType visitClassDef(YAPLParser.ClassDefContext ctx) {
        String id = ctx.TYPE(0).getText();
        String parentId = ctx.TYPE(1).getText();

        // validate new type
        if (this.types.containsType(id))
            return this.types.getType("SemError");

        // validate that inheritance type exists
        // TODO: hay tipos de los cuales no se puede heredar
        if (ctx.TYPE(1) != null && !this.types.containsType(parentId))
            return this.types.getType("SemError");

        YAPLType parent = this.types.getType(parentId);
        YAPLType newClass = new YAPLType(id, parent);
        System.out.println("New type" + id);
        this.types.addType(newClass);
        this.currentClass = newClass;

        for (YAPLParser.FeatureContext feature: ctx.feature())
            visit(feature);

        // add new scope in stack
        this.scopes.push(new YAPLSymbolTable(id));

        return this.types.getType("Object");
    }

    /**
     * (ID '(' (formal (',' formal)*)? ')' ':' TYPE '{' (expr)* '}')
     * @param ctx the parse tree
     * @return TODO
     */
    @Override
    public YAPLType visitFuncDef(YAPLParser.FuncDefContext ctx) {
        System.out.println("visitFuncDef");

        String funcId = ctx.ID().getText();
        String returnTypeId = ctx.TYPE().getText();

        // Validate return type
        if (!this.types.containsType(returnTypeId)) {
            return this.types.getType("SemError");
        }

        YAPLType returnType = this.types.getType(returnTypeId);

        // Create params list with formals
        List<YAPLType> params = new ArrayList<>();
        for (YAPLParser.FormalContext formal: ctx.formal()) {
            String id = formal.ID().getText();
            String type = formal.TYPE().getText();
            if (this.types.containsType(type)) {
                params.add(this.types.getType(type));
            } else {
                return this.types.getType("SemError");
            }
        }

        this.currentClass.getMethods().put(funcId, new YAPLMethod(funcId, returnType, params));

        return null;
    }

    /**
     * (ID ':' TYPE (ASSIGN expr)?)
     * @param ctx the parse tree
     * @return TODO
     */
    @Override
    public YAPLType visitAttrDef(YAPLParser.AttrDefContext ctx) {
        String id = ctx.ID().getText();
        String typeId = ctx.TYPE().getText();
        System.out.println("visitFuncDef" + id);


        if (!this.types.containsType(typeId)) {
            return this.types.getType("SemError");
        }

        scopes.peek().add(new YAPLSymbol(id, this.types.getType(typeId), 0));

        return super.visitAttrDef(ctx);
    }

    /**
     * @param ctx the parse tree
     * @return TODO
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
            return this.types.getType("SemError");
        }

        if (left.equals(right)) {
            System.out.println(right.getId());
            return right;
        }

        return this.types.getType("SemError");
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
            return this.types.getType("SemError");
        }

        if (left.equals(right)) {
            System.out.println(right.getId());
            return right;
        }

        return this.types.getType("SemError");
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
        return this.types.getType("SELF_TYPE");
    }

    /**
     * `false`
     * @param ctx the parse tree
     * @return Bool
     */
    @Override
    public YAPLType visitFalse(YAPLParser.FalseContext ctx) {
        System.out.println("visitFalse");
        return this.types.getType("Bool");
    }

    /**
     * `true`
     * @param ctx the parse tree
     * @return Bool
     */
    @Override
    public YAPLType visitTrue(YAPLParser.TrueContext ctx) {
        System.out.println("visitTrue");
        return this.types.getType("Bool");
    }

    /**
     * '"' (~(EOF| '"') | '\\')*  '"'
     * @param ctx the parse tree
     * @return String
     */
    @Override
    public YAPLType visitString(YAPLParser.StringContext ctx) {
        System.out.println("visitString");
        return this.types.getType("String");
    }

    /**
     * NEW TYPE
     * @param ctx the parse tree
     * @return Returns type.
     */
    @Override
    public YAPLType visitNew(YAPLParser.NewContext ctx) {
        System.out.println("visitNew");
        String typeId = ctx.TYPE().toString();
        System.out.println(typeId);

        return this.types.getType(typeId);
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
     * @return Object type
     */
    @Override
    public YAPLType visitWhile(YAPLParser.WhileContext ctx) {
        // TODO: validar que se esté obteniendo expr1
        // expr1 is not bool
        if (!visit(ctx.expr(0)).getId().equals("Bool"))
            return this.types.getType("SemError");

        return this.types.getType("Object");
    }

    /**
     * TODO:
     * ID '(' ( expr (',' expr)* )? ')'
     * @param ctx the parse tree
     * @return TODO
     */
    @Override
    public YAPLType visitDeclaration(YAPLParser.DeclarationContext ctx) {
        return super.visitDeclaration(ctx);
    }

    /**
     * TODO
     * '{' (expr ';')+ '}'
     * @param ctx the parse tree
     * @return TODO
     */
    @Override
    public YAPLType visitBrackets(YAPLParser.BracketsContext ctx) {
        int length = ctx.expr().size();
        return visit(ctx.expr(length-1));
    }

    /**
     * TODO: validar qué tipos de pueden comparar.
     * expr op=('<'|'<='|'=') expr
     * @param ctx the parse tree
     * @return Bool if both expr can be compared.
     */
    @Override
    public YAPLType visitComp(YAPLParser.CompContext ctx) {
        YAPLType leftChild = visit(ctx.expr(0)); // left expr
        YAPLType rightChild = visit(ctx.expr(1)); // right expr

        if (leftChild.equals(rightChild))
            return this.types.getType("Bool");

        return this.types.getType("SemError");
    }

    /**
     * ('~'|'not') expr
     * ~ is complement of variables of type Int
     * not is complement of variables of type Bool
     * @param ctx the parse tree
     * @return TODO
     */
    @Override
    public YAPLType visitNot(YAPLParser.NotContext ctx) {
        return switch (visit(ctx.expr()).getId()) {
            case "Int" -> this.types.getType("Int");
            case "Bool" -> this.types.getType("Bool");
            default -> this.types.getType("SemError");
        };
    }

    /**
     * @param ctx the parse tree
     * @return TODO
     */
    @Override
    public YAPLType visitLet(YAPLParser.LetContext ctx) {
        return super.visitLet(ctx);
    }

    /**
     * @param ctx the parse tree
     * @return TODO
     */
    @Override
    public YAPLType visitId(YAPLParser.IdContext ctx) {
        return super.visitId(ctx);
    }

    /**
     * @param ctx the parse tree
     * @return TODO
     */
    @Override
    public YAPLType visitFuncCall(YAPLParser.FuncCallContext ctx) {
        return super.visitFuncCall(ctx);
    }

    /**
     * @param ctx the parse tree
     * @return TODO
     */
    @Override
    public YAPLType visitIfElse(YAPLParser.IfElseContext ctx) {
        return super.visitIfElse(ctx);
    }

    /**
     * @param ctx the parse tree
     * @return TODO
     */
    @Override
    public YAPLType visitAssign(YAPLParser.AssignContext ctx) {
        return super.visitAssign(ctx);
    }
}
