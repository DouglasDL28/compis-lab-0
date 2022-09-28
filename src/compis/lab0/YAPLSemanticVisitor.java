package compis.lab0;

import java.util.List;
import java.util.Stack;

public class YAPLSemanticVisitor extends YAPLBaseVisitor<YAPLType> {

    private YAPLTypesTable types;
    private List<YAPLSemError> errors;

    private final Stack<YAPLSymbolTable> scopes = new Stack<>();

    private YAPLType currentClass = null;

    private final YAPLType objectType;
    private final YAPLType stringType;
    private final YAPLType intType;
    private final YAPLType boolType;


    public YAPLSemanticVisitor(YAPLTypesTable types, List<YAPLSemError> errors) {
        this.types = types;
        this.errors = errors;

        // Predefined types
        this.objectType = this.types.getType("Object");
        this.stringType = this.types.getType("String");
        this.intType = this.types.getType("Int");
        this.boolType = this.types.getType("Bool");
    }

    public boolean isPrimitive(String id) {
        return switch (id) {
            case "Bool", "Int", "String" -> true;
            default -> false;
        };
    }

    public YAPLSymbol getSymbol(String id) {

        for (int i=this.scopes.size()-1; i>=0; i--) {
            YAPLSymbolTable symTable = this.scopes.get(i);

            if (symTable.contains(id)) {
                return symTable.get(id);
            }
        }

        return null;
    }

    public boolean isValidAssignment(YAPLType expr, YAPLType expectedType) {
        if (expr.equals(this.boolType) && expectedType.equals(this.intType))
            return true;

        if (expr.equals(this.intType) && expectedType.equals(this.boolType))
            return true;

        return expr.equals(expectedType) || expr.isDescendantOf(expectedType);
    }

    public void createNewError(int line, int pos, String description) {
        YAPLSemError error = new YAPLSemError(line, pos, description);
        System.out.println(error);
        this.errors.add(error);
    }
    /**
     * Tree root.
     * @param ctx the parse tree
     * @return Semantic analysis type
     */
    @Override
    public YAPLType visitProgram(YAPLParser.ProgramContext ctx) {
        this.scopes.push(new YAPLSymbolTable("global"));

        YAPLType semAnalysis = null;
        for (int i=0; i<ctx.classDef().size(); i++) {
            semAnalysis = visit(ctx.classDef(i));
        }

        if (!this.types.containsType("Main")) {
            this.createNewError(0, 0, "Program must have a class Main.");
        }

        this.scopes.pop();

//        YAPLType semAnalysis = visitChildren(ctx);

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

        YAPLType parent = this.objectType;

        // inheritance defined
        if (ctx.parentId != null) {
            parent = this.types.getType(ctx.parentId.getText());

            // validate parent type
            if (!this.types.containsType(ctx.parentId.getText())) {
                this.createNewError(
                    ctx.parentId.getLine(),
                    ctx.parentId.getCharPositionInLine(),
                    "Inheritance type " + ctx.parentId.getText() + " not defined."
                );

                parent = this.objectType;
            }

            // validate inheritance from primitives
            if (isPrimitive(ctx.parentId.getText())) {
                this.createNewError(
                    ctx.parentId.getLine(),
                    ctx.parentId.getCharPositionInLine(),
                    "Can't inherit from primitive type."
                );
                parent = this.objectType;
            }

        }

        YAPLType newClass = this.types.getType(ctx.classId.getText());
        newClass.setParent(parent);
        newClass.setDepth(parent.getDepth()+1);

        // validate cyclic inheritance
        if (!parent.equals(this.objectType) && parent.isDescendantOf(newClass)) {
            this.createNewError(
                ctx.parentId.getLine(),
                ctx.parentId.getCharPositionInLine(),
                "Recursive inheritance detected in " + ctx.parentId + "."
            );
        }

        this.currentClass = newClass;

        // Create class scope
        this.scopes.push(new YAPLSymbolTable(ctx.classId.getText()));

        // class attributes
        for (YAPLParser.VarDefContext varDef : ctx.varDef()) {
            visit(varDef);
        }

        // class methods
        for (YAPLParser.FuncDefContext funcDef : ctx.funcDef()) {
            visit(funcDef);
        }

        // validate main method's existence in Main class
        if (ctx.classId.getText().equals("Main")) {
            if (!newClass.getMethods().containsKey("main()")) {
                this.createNewError(
                        ctx.classId.getLine(),
                        ctx.classId.getCharPositionInLine(),
                        "Main class must have a main method."
                );
            }
        }

        // pop class scope
        this.scopes.pop();

        this.currentClass = null; // return class to null

        return objectType;
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


        // Create params list with formals

        String signature = funcId + "(";
        for (int i=0; i<ctx.formal().size(); i++) {
            YAPLParser.FormalContext formal = ctx.formal(i);

            String id = formal.ID().getText();
            String type = formal.TYPE().getText();

            if (i == ctx.formal().size()-1) {
                signature = signature + type;
            } else {
                signature = signature + type + ", ";
            }

            // add to symbol table
            this.scopes.peek().add(new YAPLSymbol(id, this.types.getType(type), 0));
        }
        signature = signature + ")";

        YAPLMethod method = this.currentClass.getMethods().get(signature);

        for (int i=0; i < ctx.expr().size(); i ++) {
            YAPLType exprType = visit(ctx.expr(i));
            // validate return type
            // TODO: validate if polymorphism applies
            if (i == ctx.expr().size()-1 && !isValidAssignment(exprType, method.getReturnType())) {
                this.createNewError(
                        ctx.expr(i).getStart().getLine(),
                        ctx.expr(i).getStart().getCharPositionInLine(),
                        "Expression doesn't match with return type doesn't match in " + ctx.ID().getText() + " method."
                );
            }
        }

        return this.objectType;
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

        YAPLType varType = this.types.getType(typeId);

        // evaluate type's existence
        if (varType == null) {
            this.createNewError(
                    ctx.TYPE().getSymbol().getLine(),
                    ctx.TYPE().getSymbol().getCharPositionInLine(),
                    "Type " + typeId + "doesn't exist."
            );

            varType = this.objectType;
        }

        // Check expression
        if (ctx.expr() == null) {
            scopes.peek().add(new YAPLSymbol(id, varType, 0));

            return varType;
        } else {
            // evaluate assignment
            YAPLType exprType = visit(ctx.expr());

            if (!isValidAssignment(exprType, varType)) {
                this.createNewError(
                        ctx.TYPE().getSymbol().getLine(),
                        ctx.TYPE().getSymbol().getCharPositionInLine(),
                        "Expression doesn't match with type " + typeId + "."
                );

                exprType = varType; // ignore error
            }

            // add to symbol table
            scopes.peek().add(new YAPLSymbol(id, exprType, 0));

            return exprType;
        }
    }

    /**
     * ID ':' TYPE
     * @param ctx the parse tree
     * @return No type
     */
    @Override
    public YAPLType visitFormal(YAPLParser.FormalContext ctx) {
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
        YAPLType left = visit(ctx.expr1); // left expr
        YAPLType right = visit(ctx.expr2); // right expr

        if (left.equals(right)) {
            return this.intType;
        }

        this.createNewError(
                ctx.expr1.getStart().getLine(),
                ctx.expr1.getStart().getCharPositionInLine(),
                "Can't do operation with " + left.getId() + " and " + right.getId() + "."
        );

        return this.intType; // default value
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
        YAPLType left = visit(ctx.expr1); // left expr
        YAPLType right = visit(ctx.expr2); // right expr

        if (left.equals(right)) {
            return this.intType;
        }

        this.createNewError(
                ctx.expr1.getStart().getLine(),
                ctx.expr1.getStart().getCharPositionInLine(),
                "Can't do operation with " + left.getId() + " and " + right.getId() + "."
        );

        return this.intType;
    }

    /**
     * `self`
     *
     * @param ctx the parse tree
     * @return SELF_TYPE
     */
    @Override
    public YAPLType visitSelf(YAPLParser.SelfContext ctx) {
        return this.currentClass;
    }

    /**
     * `false`
     * @param ctx the parse tree
     * @return Bool
     */
    @Override
    public YAPLType visitFalse(YAPLParser.FalseContext ctx) {
        return this.boolType;
    }

    /**
     * `true`
     * @param ctx the parse tree
     * @return Bool
     */
    @Override
    public YAPLType visitTrue(YAPLParser.TrueContext ctx) {
        return this.boolType;
    }

    /**
     * '"' (~(EOF| '"') | '\\')*  '"'
     * @param ctx the parse tree
     * @return String
     */
    @Override
    public YAPLType visitString(YAPLParser.StringContext ctx) {
        return this.stringType;
    }

    /**
     * NEW TYPE
     * @param ctx the parse tree
     * @return Returns type.
     */
    @Override
    public YAPLType visitNew(YAPLParser.NewContext ctx) {
        if (!this.types.containsType(ctx.TYPE().getText())) {
            this.createNewError(
                    ctx.TYPE().getSymbol().getLine(),
                    ctx.TYPE().getSymbol().getCharPositionInLine(),
                    "Type " + ctx.TYPE().getText() + " is not defined."
            );

            return this.objectType; // default return type
        }

        return this.types.getType(ctx.TYPE().getText());
    }

    /**
     * '(' expr ')'
     * Returns expr type.
     * @param ctx the parse tree
     * @return expr type
     */
    @Override
    public YAPLType visitParens(YAPLParser.ParensContext ctx) {
        return visit(ctx.expr());
    }

    /**
     * ISVOID expr
     * @param ctx the parse tree
     * @return Bool
     */
    @Override
    public YAPLType visitIsvoid(YAPLParser.IsvoidContext ctx) {
        return this.boolType;
    }

    /**
     * INT
     * @param ctx the parse tree
     * @return Int
     */
    @Override
    public YAPLType visitInteger(YAPLParser.IntegerContext ctx) {
        return this.intType;
    }

    /**
     * TODO: No recuerdo cómo era esto
     * WHILE expr LOOP expr POOL
     * @param ctx the parse tree
     * @return Object type
     */
    @Override
    public YAPLType visitWhile(YAPLParser.WhileContext ctx) {
       // expr1 is not bool
        YAPLType whileExprType = visit(ctx.expr(0));
        if (!whileExprType.equals(this.boolType)) {
            this.createNewError(
                    ctx.expr(0).getStart().getLine(),
                    ctx.expr(0).getStart().getCharPositionInLine(),
                    "While expression must be of type Bool."
            );
        }

        visit(ctx.expr(1));

        return this.objectType;
    }

    /**
     * ID '(' ( expr (',' expr)* )? ')'
     * @param ctx the parse tree
     * @return method return type
     */
    @Override
    public YAPLType visitDeclaration(YAPLParser.DeclarationContext ctx) {
        String methodId = ctx.ID().getText();

        StringBuilder signature = new StringBuilder(methodId + "(");
        for (int i=0; i < ctx.expr().size(); i++) {
            YAPLType paramType = visit(ctx.expr(i));

            if (i < ctx.expr().size()-1) {
                signature.append(paramType.getId()).append(", ");
            } else {
                signature.append(paramType.getId());
            }
        }
        signature.append(")");

        if (!this.currentClass.getMethods().containsKey(signature.toString())) {
            this.createNewError(
                    ctx.ID().getSymbol().getLine(),
                    ctx.ID().getSymbol().getCharPositionInLine(),
                    "No method " + signature + " in " + this.currentClass.getId() + "."
            );

            return this.objectType;
        }

        return this.currentClass.getMethods().get(signature.toString()).getReturnType();
    }

    /**
     * '{' (expr ';')+ '}'
     * @param ctx the parse tree
     * @return last expressions' type
     */
    @Override
    public YAPLType visitBrackets(YAPLParser.BracketsContext ctx) {
        YAPLType returnType = this.objectType; // default
        for (int i=0; i<ctx.expr().size(); i++) {
            if (i < ctx.expr().size()-1) {
                visit(ctx.expr(i));
            } else {
                returnType = visit(ctx.expr(i));
            }
        }

        return returnType;
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

        if (
                leftChild.equals(rightChild)
                || (leftChild.getId().equals("Int") && rightChild.getId().equals("Bool"))
                || (leftChild.getId().equals("Bool") && rightChild.getId().equals("Int"))
        )
            return this.types.getType("Bool");

        this.createNewError(
                ctx.start.getLine(),
                ctx.start.getCharPositionInLine(),
                "Can't compare type " + leftChild + " and " + rightChild + "."
        );

        return this.boolType;
    }

    /**
     * '~' expr
     * ~ is complement of variables of type Int
     * not is complement of variables of type Bool
     * @param ctx the parse tree
     * @return TODO
     */
    @Override
    public YAPLType visitIntComplement(YAPLParser.IntComplementContext ctx) {
        // bool
        YAPLType exprType = visit(ctx.expr());

        if (!exprType.equals(this.intType)) {
            this.createNewError(
                    ctx.expr().start.getLine(),
                    ctx.expr().start.getCharPositionInLine(),
                    "Expression type must be of type Int."
            );
        }


        return this.intType; // ignore error
    }

    /**
     * '~' expr
     * ~ is complement of variables of type Int
     * not is complement of variables of type Bool
     * @param ctx the parse tree
     * @return TODO
     */
    @Override
    public YAPLType visitBoolComplement(YAPLParser.BoolComplementContext ctx) {
        // bool
        YAPLType exprType = visit(ctx.expr());

        if (!exprType.equals(this.boolType)) {
            this.createNewError(
                    ctx.expr().start.getLine(),
                    ctx.expr().start.getCharPositionInLine(),
                    "Expression type must be of type Bool."
            );
        }

        return this.boolType; // ignore error
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

        // evaluate variables declarations
        for (YAPLParser.VarDefContext varDef : ctx.varDef()) {
            String id = varDef.ID().getText();
            String typeId = varDef.TYPE().getText();

            YAPLType type = this.types.getType(typeId);

            if (type == null) {
                this.createNewError(
                        varDef.TYPE().getSymbol().getLine(),
                        varDef.TYPE().getSymbol().getCharPositionInLine(),
                        "Type " + typeId + "doesn't exist."
                );

                type = this.objectType; // ignore error
            }

            YAPLType assignmentType = type;

            // validate that expression type match
            if (varDef.expr() != null) {
                assignmentType = visit(varDef.expr());

                // validate expression type
                if (!isValidAssignment(assignmentType, type)) {
                    this.createNewError(
                            varDef.expr().start.getLine(),
                            varDef.expr().start.getCharPositionInLine(),
                            "Expression doesn't match type " + typeId + "."
                    );
                    assignmentType = type;
                }
            }

            // add to symbol table
            if (!this.scopes.peek().contains(id)) {
                this.scopes.peek().add(new YAPLSymbol(id, this.types.getType(typeId), 0));
            } else {
                this.createNewError(
                        varDef.ID().getSymbol().getLine(),
                        varDef.ID().getSymbol().getCharPositionInLine(),
                        "Variable " + id + " already defined in scope."
                );
            }
        }

        YAPLType exprType = visit(ctx.expr());

        this.scopes.pop(); // pop scope

        // return last expr type
        return exprType;
    }

    /**
     * ID
     * @param ctx the parse tree
     * @return ID's type
     */
    @Override
    public YAPLType visitId(YAPLParser.IdContext ctx) {
        String id = ctx.ID().getText();

        YAPLSymbol symbol = getSymbol(id);

        if (symbol == null) {
            this.createNewError(
                    ctx.ID().getSymbol().getLine(),
                    ctx.ID().getSymbol().getCharPositionInLine(),
                    "No symbol " + id + "."
            );

            return this.objectType;
        }

        return symbol.getType();
    }

    /**
     * expr ('@' TYPE)? '.' ID '(' (expr (',' expr)*)? ')'
     * @param ctx the parse tree
     * @return method return type
     */
    @Override
    public YAPLType visitFuncCall(YAPLParser.FuncCallContext ctx) {
        YAPLType exprType = visit(ctx.expr(0));

        // Validate type
        if (ctx.TYPE() != null && this.types.containsType(ctx.TYPE().getText())) {
            exprType = this.types.getType(ctx.TYPE().getText());
        }

        String id = ctx.ID().getText();

        StringBuilder signature = new StringBuilder(id + "(");
        for (int i=1; i < ctx.expr().size(); i++) {
            YAPLType paramType = visit(ctx.expr(i));

            if (i < ctx.expr().size()-1) {
                signature.append(paramType.getId()).append(", ");
            } else {
                signature.append(paramType.getId());
            }
        }
        signature.append(")");


        if (!exprType.getMethods().containsKey(signature.toString())) {
            this.createNewError(
                    ctx.expr(0).getStart().getLine(),
                    ctx.expr(0).getStart().getCharPositionInLine(),
                    "Type " + exprType.getId() + " doesn't contain method " + signature + "."
            );

            return this.objectType;
        }

        YAPLMethod method = exprType.getMethods().get(signature.toString());

        return method.getReturnType();
    }

    /**
     * IF expr THEN expr ELSE expr FI
     * @param ctx the parse tree
     * @return supertype of both expressions
     */
    @Override
    public YAPLType visitIfElse(YAPLParser.IfElseContext ctx) {

        // validate if expression type
        if (!visit(ctx.expr(0)).equals(this.boolType)) {
            this.createNewError(
                    ctx.expr(0).start.getLine(),
                    ctx.expr(0).start.getCharPositionInLine(),
                    "If expression must be of type Bool."
            );
        }

        YAPLType thenType = visit(ctx.expr(1));
        YAPLType elseType = visit(ctx.expr(2));

        return thenType.commonAncestorWith(elseType);
    }

    /**
     * TODO: hay que validar en la tabla de símbolos
     * ID ASSIGN expr
     * @param ctx the parse tree
     * @return TODO
     */
    @Override
    public YAPLType visitAssign(YAPLParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        YAPLType exprType = visit(ctx.expr());

        YAPLSymbol symbol = getSymbol(id); // get symbol from first symtable

        if (symbol == null) {
            this.createNewError(
                    ctx.ID().getSymbol().getLine(),
                    ctx.ID().getSymbol().getCharPositionInLine(),
                    "Id not defined."
            );

            symbol = new YAPLSymbol(id, exprType, 0);
        }

        if (!isValidAssignment(exprType, symbol.getType())) {
            this.createNewError(
                    ctx.expr().start.getLine(),
                    ctx.expr().start.getCharPositionInLine(),
                    "Expression type must be of type " + symbol.getType().getId() + "."
            );

            exprType = symbol.getType(); // ignore errors
        }

        return exprType;
    }
}
