import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class YAPLSemanticVisitor extends YAPLBaseVisitor<YAPLType> {

    private YAPLTypesTable types;
//    private YAPLScopesStack scopes = new YAPLScopesStack();

    private final Stack<YAPLSymbolTable> scopes = new Stack<>();

    private YAPLType currentClass = null;


    public YAPLSemanticVisitor(YAPLTypesTable types) {
        this.types = types;
    }

    /**
     * Tree root.
     * @param ctx the parse tree
     * @return Semantic analysis type
     */
    @Override
    public YAPLType visitProgram(YAPLParser.ProgramContext ctx) {
        System.out.println("visitProgram");
        this.scopes.push(new YAPLSymbolTable("global"));

        YAPLType semAnalysis = null;
        for (int i=0; i<ctx.classDef().size(); i++) {
            semAnalysis = visit(ctx.classDef(i));
            System.out.println(semAnalysis.getId());

            if (semAnalysis.equals(this.types.getType("SemError"))) {
                System.out.println("Error");
            }
        }

//        YAPLType semAnalysis = visitChildren(ctx);


        System.out.println("Análisis semántico exitoso!");
        return semAnalysis;
    }

    /**
     * TODO: que valor tiene?
     * CLASS TYPE (INHERITS TYPE)? '{' ((funcDef | varDef) ';')* '}' ';'
     * @param ctx the parse tree
     * @return TODO
     */
    @Override
    public YAPLType visitClassDef(YAPLParser.ClassDefContext ctx) {
        String id = ctx.TYPE(0).getText();
        String parentId = ctx.TYPE(1).getText();
        System.out.println("visitClassDef " + id);

        // validate new type
        if (this.types.containsType(id)) {
            System.out.println("SemError! Type is already defined.");
            return this.types.getType("SemError");
        }

        // validate that inheritance type exists
        // TODO: hay tipos de los cuales no se puede heredar
        if (ctx.TYPE(1) != null && !this.types.containsType(parentId)) {
            System.out.println("\tSemError! No type " + parentId);
            return this.types.getType("SemError");
        }

        YAPLType parent = this.types.getType(parentId);
        YAPLType newClass = new YAPLType(id, parent, parent.getDepth()+1);

        // validate cyclic inheritance (new type is ancestor of parent)
        if (parent.isDescendantOf(newClass)) {
            System.out.println("\tSemError! Cyclic inheritance detected in type " + parentId + ".");
            return this.types.getType("SemError");
        }

        while (!parent.equals(this.types.getType("Object"))) {
            for (Map.Entry<String, YAPLMethod> method : parent.getMethods().entrySet()) {
                newClass.getMethods().put(method.getKey(), method.getValue());
            }
            parent = parent.getParent();
        }

        System.out.println("\tNew type " + id);
        this.types.addType(newClass);
        this.currentClass = newClass;

        // class methods
        for (YAPLParser.FuncDefContext funcDef: ctx.funcDef()) {
            if (visit(funcDef).equals(this.types.getType("SemError"))) {
                System.out.println("\tSemError! In method definition. " + funcDef.ID().getText());
                return this.types.getType("SemError");
            }
        }
        // class attributes
        for (YAPLParser.VarDefContext varDef: ctx.varDef()) {
            if(visit(varDef).equals(this.types.getType("SemError"))) {
                System.out.println("\tSemError! In attribute definition. " + varDef.ID().getText());
                return this.types.getType("SemError");
            }
        }

        // add new scope in stack
        this.scopes.push(new YAPLSymbolTable(id));

        return this.types.getType("Object");
    }

    /**
     * Method definition
     * (ID '(' (formal (',' formal)*)? ')' ':' TYPE '{' (expr)* '}')
     *
     * @param ctx the parse tree
     * @return TODO
     */
    @Override
    public YAPLType visitFuncDef(YAPLParser.FuncDefContext ctx) {

        String funcId = ctx.ID().getText();
        String returnTypeId = ctx.TYPE().getText();

        System.out.println("visitFuncDef" + funcId);

        // Validate return type
        if (!this.types.containsType(returnTypeId)) {
            System.out.println("\tSemError!");
            return this.types.getType("SemError");
        }

        YAPLType returnType = this.types.getType(returnTypeId);

        // Create params list with formals
        List<YAPLType> params = new ArrayList<>();
        for (YAPLParser.FormalContext formal: ctx.formal()) {
            String id = formal.ID().getText();
            String type = formal.TYPE().getText();

            // validate type's existence
            if (!this.types.containsType(type)) {
                System.out.println("\tSemError!" + type + "for " + id + " doesn't exist.");
                return this.types.getType("SemError");
            }

            params.add(this.types.getType(type));

            // add to symbol table
            this.scopes.peek().add(new YAPLSymbol(id, this.types.getType(type), 0));
        }

        YAPLMethod newMethod = new YAPLMethod(funcId, returnType, params);
        System.out.println("\t" + newMethod);

        this.currentClass.getMethods().put(funcId, newMethod);

        for (YAPLParser.ExprContext expr: ctx.expr()) {
            YAPLType exprType = visit(expr);
            if (exprType.equals(this.types.getType("SemError"))) {
                System.out.println("\tSemError! Evaluating expression:" +
                        "\n\t\t" + expr.getText());
                return this.types.getType("SemError");
            }
        }

        // validate return type
        if (visit(ctx.expr(ctx.expr().size()-1)) != returnType) {
            System.out.println("\t SemError! Return type doesn't match.");
            return this.types.getType("SemError");
        }

        return this.types.getType("Object");
    }

    /**
     * Attribute definition
     * (ID ':' TYPE (ASSIGN expr)?)
     * TODO: chequear polimorfismo
     * @param ctx the parse tree
     * @return TODO
     */
    @Override
    public YAPLType visitVarDef(YAPLParser.VarDefContext ctx) {
        String id = ctx.ID().getText();
        String typeId = ctx.TYPE().getText();

        System.out.println("visitVarDef " + id + " : " + typeId); // debugging

        // evaluate type's existence
        if (!this.types.containsType(typeId)) {
            System.out.println("SemError! type " + typeId + "doesn't exist.");
            return this.types.getType("SemError");
        }

        // evaluate assignment
        YAPLType exprType = visit(ctx.expr());
        if (!this.types.getType(typeId).equals(exprType) // type doesn't match
            && !exprType.isDescendantOf(this.types.getType(typeId)))  // type is not ancestor
        {
            System.out.println("SemError! Type doesn't match.");
            return this.types.getType("SemError");
        }

        // add to symbol table
        scopes.peek().add(new YAPLSymbol(id, exprType, 0));

        System.out.println(exprType.getId());
        return exprType;
    }

    /**
     * ID ':' TYPE
     * @param ctx the parse tree
     * @return TODO
     */
    @Override
    public YAPLType visitFormal(YAPLParser.FormalContext ctx) {
        System.out.println("visitFormal" + ctx.ID().getText());
        return super.visitFormal(ctx);
    }

    /**
     * `left op=('+'|'-') right`
     *
     * if left.type == right.type return right.type else SemError
     *
     * @param ctx the parse tree
     * @return YAPlType
     */
    @Override
    public YAPLType visitMulDiv(YAPLParser.MulDivContext ctx) {
        System.out.println("visitMulDiv" + ctx.getText());

        YAPLType left = visit(ctx.expr(0)); // left expr
        YAPLType right = visit(ctx.expr(1)); // right expr

        if (left.getId().equals("SemError") || right.getId().equals("SemError")) {
            System.out.println("SemError! In multiplication.");
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
        System.out.println("visitAddSub" + ctx.getText());

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
        System.out.println("visitFalse" + ctx.getText());
        return this.types.getType("Bool");
    }

    /**
     * `true`
     * @param ctx the parse tree
     * @return Bool
     */
    @Override
    public YAPLType visitTrue(YAPLParser.TrueContext ctx) {
        System.out.println("visitTrue" + ctx.getText());
        return this.types.getType("Bool");
    }

    /**
     * '"' (~(EOF| '"') | '\\')*  '"'
     * @param ctx the parse tree
     * @return String
     */
    @Override
    public YAPLType visitString(YAPLParser.StringContext ctx) {
        System.out.println("visitString " + ctx.getText());
        return this.types.getType("String");
    }

    /**
     * NEW TYPE
     * @param ctx the parse tree
     * @return Returns type.
     */
    @Override
    public YAPLType visitNew(YAPLParser.NewContext ctx) {
        System.out.println("visitNew"  + ctx.getText());
        String typeId = ctx.TYPE().toString();

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
        System.out.println("visitIsVoid");

        return this.types.getType("Bool");
    }

    /**
     * INT
     * @param ctx the parse tree
     * @return Int
     */
    @Override
    public YAPLType visitInteger(YAPLParser.IntegerContext ctx) {
        System.out.println("visitInteger " + ctx.getText());
        return this.types.getType("Int");
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
        if (!visit(ctx.expr(0)).getId().equals("Bool")) {
            System.out.println("SemError! Expr in while must be boolean.");
            return this.types.getType("SemError");
        }

        return this.types.getType("Object");
    }

    /**
     * ID '(' ( expr (',' expr)* )? ')'
     * @param ctx the parse tree
     * @return method return type
     */
    @Override
    public YAPLType visitDeclaration(YAPLParser.DeclarationContext ctx) {
        String methodId = ctx.ID().getText();
        System.out.println("visitDeclaration " + methodId);

        if (!this.currentClass.getMethods().containsKey(methodId)) {
            System.out.println("SemError! Method " + methodId + "doesn't exist.");
            return this.types.getType("SemError");
        }

        return this.currentClass.getMethods().get(methodId).getReturnType();
    }

    /**
     * '{' (expr ';')+ '}'
     * @param ctx the parse tree
     * @return last expressions' type
     */
    @Override
    public YAPLType visitBrackets(YAPLParser.BracketsContext ctx) {
        System.out.println("visitBrackets");
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
        System.out.println("visitComp" + ctx.getText());
        YAPLType leftChild = visit(ctx.expr(0)); // left expr
        YAPLType rightChild = visit(ctx.expr(1)); // right expr

        if (
                leftChild.equals(rightChild)
                || (leftChild.getId().equals("Int") && rightChild.getId().equals("Bool"))
                || (leftChild.getId().equals("Bool") && rightChild.getId().equals("Int"))
        )
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
        System.out.println("visitNot" + ctx.getText());
        // bool
        if (ctx.NOT() != null) {
            if (visit(ctx.expr()).getId().equals("Bool")) {
                return this.types.getType("Bool");
            }
        } else { // ~
            if (visit(ctx.expr()).getId().equals("Int")) {
                return this.types.getType("Int");
            }
        }

        return this.types.getType("SemError");
    }

    /**
     * TODO: validar con nueva gramática
     * LET  varDef (',' varDef)* IN expr
     * @param ctx the parse tree
     * @return last expr type
     */
    @Override
    public YAPLType visitLet(YAPLParser.LetContext ctx) {

        // add new scope for let
        this.scopes.push(new YAPLSymbolTable("let"));

        // evaluate declarations
        for (YAPLParser.VarDefContext varDef : ctx.varDef()) {
            String id = varDef.ID().getText();
            String typeId = varDef.TYPE().getText();

            if (!this.types.containsType(typeId)) {
                System.out.println("SemError! Type " + typeId + " doesn't exist.");
                return this.types.getType("SemError");
            }

            // validate that expression type match
            if (varDef.expr() != null && !visit(varDef.expr()).equals(this.types.getType(typeId))) {
                System.out.println("SemError! Expression doesn't match type for " + id);
                return this.types.getType("SemError");
            }

            // add to symbol table
            if (this.scopes.peek().contains(id)) {
                this.scopes.peek().add(new YAPLSymbol(id, this.types.getType(typeId), 0));
            } else {
                return this.types.getType("SemError");
            }
        }

//        for (int i=0; i < ctx.varDef().size(); i++) {
//            String id = (i).getText();
//            String typeId = ctx.TYPE(i).getText();
//            YAPLType exprType = visit(ctx.expr(i));
//
//            if (this.types.containsType(typeId))
//                return this.types.getType("SemError");
//
//            if (!exprType.isDescendantOf(this.types.getType(typeId)))
//                return this.types.getType("SemError");
//
//            // add to symbol table
//            if (!this.scopes.peek().contains(id)) {
//                this.scopes.peek().add(new YAPLSymbol(id, exprType, 0));
//            } else { // already defined in scope
//                return this.types.getType("SemError");
//            }
//        }

        // return last expr type
        return visit(ctx.expr());
    }

    /**
     * ID
     * @param ctx the parse tree
     * @return ID's type
     */
    @Override
    public YAPLType visitId(YAPLParser.IdContext ctx) {
        System.out.println("visitId" + ctx.getText());
        String id = ctx.ID().getText();
        if (!this.scopes.peek().contains(id))
            return this.types.getType("SemError");

        return this.scopes.peek().get(id).getType();
    }

    /**
     * expr ('@' TYPE)? '.' ID '(' (expr (',' expr)*)? ')'
     * @param ctx the parse tree
     * @return method return type
     */
    @Override
    public YAPLType visitFuncCall(YAPLParser.FuncCallContext ctx) {
        System.out.println("visitFuncCall " + ctx.getText());

        YAPLType exprType = visit(ctx.expr(0));

        if (ctx.TYPE() != null && this.types.containsType(ctx.TYPE().getText())) {
            exprType = this.types.getType(ctx.TYPE().getText());
        }

        String id = ctx.ID().getText();

        if (!exprType.getMethods().containsKey(id))
            return this.types.getType("SemError");

        YAPLMethod method = exprType.getMethods().get(id);

        for (int i=1; i < ctx.expr().size(); i++) {
            YAPLType paramType = visit(ctx.expr(i));

            if (!paramType.isDescendantOf(method.getParams().get(i-1)))
                return this.types.getType("SemError");
        }

        return method.getReturnType();
    }

    /**
     * IF expr THEN expr ELSE expr FI
     * @param ctx the parse tree
     * @return TODO: El tipo de dato del condicional if es el tipo de dato del bloque que sea un supertipo de ambas ramas del condicional.
     */
    @Override
    public YAPLType visitIfElse(YAPLParser.IfElseContext ctx) {

        if (visit(ctx.expr(0)) != this.types.getType("Bool")) {
            System.out.printf("SemError! Expression must be boolean.");
            return this.types.getType("SemError");
        }

        YAPLType thenType = visit(ctx.expr(1));
        YAPLType elseType = visit(ctx.expr(2));

        return thenType.commonAncestorWith(elseType);
//        return super.visitIfElse(ctx);
    }

    /**
     * ID ASSIGN expr
     * @param ctx the parse tree
     * @return TODO
     */
    @Override
    public YAPLType visitAssign(YAPLParser.AssignContext ctx) {
        System.out.println("visitAssign" + ctx.getText());
        return super.visitAssign(ctx);
    }
}
