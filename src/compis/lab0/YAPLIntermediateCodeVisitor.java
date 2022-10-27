package compis.lab0;

import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.util.*;

public class YAPLIntermediateCodeVisitor extends YAPLBaseVisitor<YAPLType> {

    private final YAPLTypesTable types;

    private final List<Quad> intermediateCode;

    private final Stack<Integer> offsets = new Stack<>();
    private final Stack<YAPLSymbolTable> scopes = new Stack<>();
    private final Stack<YAPLSymbolTable> temps = new Stack<>();
    private int labelCount = 0;

    private final ParseTreeProperty<String> addresses = new ParseTreeProperty<>();

    private YAPLType currentClass = null;
    private Stack<String> interCodeScope = new Stack<>();

    private final YAPLType objectType;
    private final YAPLType stringType;
    private final YAPLType intType;
    private final YAPLType boolType;


    public YAPLIntermediateCodeVisitor(YAPLTypesTable types, List<Quad> interCode) {
        this.types = types;
        this.intermediateCode = interCode;

        // Predefined types
        this.objectType = this.types.getType("Object");
        this.stringType = this.types.getType("String");
        this.intType = this.types.getType("Int");
        this.boolType = this.types.getType("Bool");
    }

    public void generateQuad(String op, String arg1, String arg2, String result) {
        Quad quad = new Quad(op, arg1, arg2, result);
        System.out.println(quad);

        this.intermediateCode.add(quad);
    }

    public YAPLSymbol generateTemp(YAPLType type) {
        YAPLSymbolTable top = this.temps.peek();

        YAPLSymbol temp = new YAPLSymbol("temp", type, type.getWidth(), this.offsets.peek(), this.interCodeScope.peek());
        this.offsets.push(this.offsets.pop() + type.getWidth());
        top.add(temp);

        return temp;
    }

    public String getCodeFromSymbol(YAPLSymbol symbol) {
        return symbol.getScope() + "[" + symbol.getOffset().toString() + "]";
    }

    /**
     * Generate new Label.
     * @return new unique label
     */
    public String genLabel() {
        String newLabel = "L" + this.labelCount;
        this.labelCount++;

        return newLabel;
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

    /**
     * Tree root.
     * @param ctx the parse tree
     * @return Semantic analysis type
     */
    @Override
    public YAPLType visitProgram(YAPLParser.ProgramContext ctx) {
        this.scopes.push(new YAPLSymbolTable("global")); // symbol table
        this.temps.push(new YAPLSymbolTable("global"));  // temps
        this.interCodeScope.push("global");
        this.offsets.push(0); // add new offset

        YAPLType semAnalysis = null;
        for (int i=0; i<ctx.classDef().size(); i++) {
            semAnalysis = visit(ctx.classDef(i));
        }

        // pop everything from stacks
        this.scopes.pop();
        this.temps.pop();
        this.offsets.pop();

//        YAPLType semAnalysis = visitChildren(ctx);

        return semAnalysis;
    }

    /**
     * CLASS TYPE (INHERITS TYPE)? '{' ((funcDef | varDef) ';')* '}' ';'
     * @param ctx the parse tree
     * @return Object type
     */
    @Override
    public YAPLType visitClassDef(YAPLParser.ClassDefContext ctx) {

        this.temps.push(new YAPLSymbolTable(ctx.classId.getText())); // Create temps scope
        this.scopes.push(new YAPLSymbolTable(ctx.classId.getText())); // Create class scope
        this.interCodeScope.push(ctx.classId.getText());
        this.offsets.push(0); // new offset

        YAPLType parent = this.objectType;

        // inheritance defined
        if (ctx.parentId != null) {
            parent = this.types.getType(ctx.parentId.getText());
        }

        YAPLType newClass = this.types.getType(ctx.classId.getText());
        newClass.setParent(parent);
        newClass.setDepth(parent.getDepth()+1);

        this.currentClass = newClass;

        this.generateQuad(this.currentClass.getId() + ":", null, null, null);

        // class attributes
        for (YAPLParser.VarDefContext varDef : ctx.varDef()) {
            visit(varDef);
        }

        // class methods
        for (YAPLParser.FuncDefContext funcDef : ctx.funcDef()) {
            visit(funcDef);
        }

        // pop from all stacks
        this.scopes.pop();
        this.offsets.pop();
        this.temps.pop();
        this.interCodeScope.pop();

        this.currentClass = null; // return class to null

        return this.objectType;
    }

    /**
     * Method definition
     * (ID '(' (formal (',' formal)*)? ')' ':' TYPE '{' (expr)* '}')
     *
     * @param ctx the parse tree
     * @return Object type
     */
    @Override
    public YAPLType visitFuncDef(YAPLParser.FuncDefContext ctx) {

        String funcId = ctx.ID().getText();
        String returnTypeId = ctx.TYPE().getText();

        this.scopes.push(new YAPLSymbolTable(funcId));
        this.temps.push(new YAPLSymbolTable(funcId));
        this.interCodeScope.push(funcId);
        this.offsets.push(0);

        this.generateQuad(this.currentClass.getId() + "." + funcId + ":", null, null, null);

        // Create params list with formals
        StringBuilder signature = new StringBuilder(funcId + "(");
        for (int i=0; i<ctx.formal().size(); i++) {
            YAPLParser.FormalContext formal = ctx.formal(i);

            String id = formal.ID().getText();
            String type = formal.TYPE().getText();

            if (i == ctx.formal().size()-1) {
                signature.append(type);
            } else {
                signature.append(type).append(", ");
            }

            // add to symbol table
            YAPLType varType = this.types.getType(type);
            YAPLSymbolTable symTableTop = this.scopes.peek();

            YAPLSymbol symbol = new YAPLSymbol(id, varType, varType.getWidth(), this.offsets.peek(), this.interCodeScope.peek());
            symTableTop.add(symbol);
            this.offsets.push(this.offsets.pop() + varType.getWidth()); // update offset

            this.generateQuad(
                    "=",
                    varType.getDefaultVal(),
                    this.getCodeFromSymbol(symbol),
                    null
            );
        }

        signature.append(")");

        YAPLMethod method = this.currentClass.getMethods().get(signature.toString());

        for (int i=0; i < ctx.expr().size(); i ++) {
            YAPLType exprType = visit(ctx.expr(i));

            if (i == ctx.expr().size()-1) { // last expr
                // create return val temp
                YAPLSymbol returnValTemp = this.generateTemp(exprType);

                this.generateQuad(
                        "=",
                        this.addresses.get(ctx.expr(i).getRuleContext()),
                        this.getCodeFromSymbol(returnValTemp),
                        null
                );

                this.generateQuad("return", this.getCodeFromSymbol(returnValTemp), null, null);

                // set ctx address to return temp value
                this.addresses.put(ctx, this.getCodeFromSymbol(returnValTemp));
            }
        }

        // pop top in scopes and temps stacks
        this.scopes.pop();
        this.temps.pop();
        this.interCodeScope.pop();
        this.offsets.pop();

        return this.objectType;
    }

    /**
     * Attribute definition
     * (ID ':' TYPE (ASSIGN expr)?)
     * @param ctx the parse tree
     * @return exprType
     */
    @Override
    public YAPLType visitVarDef(YAPLParser.VarDefContext ctx) {
        String id = ctx.ID().getText();
        String typeId = ctx.TYPE().getText();

        YAPLType varType = this.types.getType(typeId);

        // Check expression
        if (ctx.expr() == null) { // no assign
            YAPLSymbol symbol = new YAPLSymbol(id, varType, varType.getWidth(), 0, this.interCodeScope.peek());
            scopes.peek().add(symbol);

            this.generateQuad(
                    "=",
                    varType.getDefaultVal(),
                    this.getCodeFromSymbol(symbol),
                    null
            );

            this.addresses.put(ctx, this.getCodeFromSymbol(symbol));

            return varType;
        } else {
            // evaluate assignment
            YAPLType exprType = visit(ctx.expr());

            // add to symbol table
            YAPLSymbol newSymbol = new YAPLSymbol(id, exprType, exprType.getWidth(), this.offsets.peek(), this.interCodeScope.peek());
            scopes.peek().add(newSymbol);
            this.offsets.push(this.offsets.pop() + exprType.getWidth());

            this.generateQuad(
                    "=",
                    this.addresses.get(ctx.expr().getRuleContext()),
                    this.getCodeFromSymbol(newSymbol),
                    null
            );

            this.addresses.put(ctx, this.getCodeFromSymbol(newSymbol));

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

        this.temps.push(new YAPLSymbolTable("MulDiv"));

        YAPLSymbol temp = generateTemp(this.intType);

        String op = ctx.op.getText().equals("*") ? "MUL" : "DIV";
        this.generateQuad(
                op,
                this.addresses.get(ctx.expr1.getRuleContext()),
                this.addresses.get(ctx.expr2.getRuleContext()),
                this.getCodeFromSymbol(temp)
        );

        this.addresses.put(ctx, this.getCodeFromSymbol(temp));

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

        this.temps.push(new YAPLSymbolTable("AddSub"));

        YAPLSymbol temp = this.generateTemp(this.intType);

        String op = ctx.op.getText().equals("+") ? "ADD" : "SUB";
        this.generateQuad(
                op,
                this.addresses.get(ctx.expr1.getRuleContext()),
                this.addresses.get(ctx.expr2.getRuleContext()),
                this.getCodeFromSymbol(temp)
        );

        this.addresses.put(ctx, this.getCodeFromSymbol(temp));

        if (left.equals(right)) {
            return this.intType;
        }

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
        this.addresses.put(ctx, this.currentClass.getId() + "[0]");
        return this.currentClass;
    }

    /**
     * `false`
     * @param ctx the parse tree
     * @return Bool
     */
    @Override
    public YAPLType visitFalse(YAPLParser.FalseContext ctx) {
        this.addresses.put(ctx, "FALSE");
        return this.boolType;
    }

    /**
     * `true`
     * @param ctx the parse tree
     * @return Bool
     */
    @Override
    public YAPLType visitTrue(YAPLParser.TrueContext ctx) {
        this.addresses.put(ctx, "TRUE");
        return this.boolType;
    }

    /**
     * '"' (~(EOF| '"') | '\\')*  '"'
     * @param ctx the parse tree
     * @return String
     */
    @Override
    public YAPLType visitString(YAPLParser.StringContext ctx) {
        // TODO: revisar si debe ser puntero
        this.addresses.put(ctx, ctx.getText());
        return this.stringType;
    }

    /**
     * NEW TYPE
     * @param ctx the parse tree
     * @return Returns type.
     */
    @Override
    public YAPLType visitNew(YAPLParser.NewContext ctx) {
        // create return val temp
        this.generateQuad("param", ctx.TYPE().getText(), null, null);
        YAPLSymbol returnValTemp = this.generateTemp(this.objectType);

        // set ctx address to return temp value
        this.addresses.put(ctx, this.getCodeFromSymbol(returnValTemp));

        // call intermediate code
        // returnTempVal = call funcID, param.length
        this.generateQuad(
                ",",
                "call new",
                String.valueOf(1),
                this.getCodeFromSymbol(returnValTemp)
        );
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
        YAPLType exprType = visit(ctx.expr());
        this.addresses.put(ctx, this.addresses.get(ctx.expr().getRuleContext()));
        return exprType;
    }

    /**
     * ISVOID expr
     * @param ctx the parse tree
     * @return Bool
     */
    @Override
    public YAPLType visitIsvoid(YAPLParser.IsvoidContext ctx) {
        YAPLSymbol temp = this.generateTemp(this.boolType);
        visit(ctx.expr());

        this.generateQuad(
                "=",
                "VOID",
                this.addresses.get(ctx.expr().getRuleContext()),
                this.getCodeFromSymbol(temp)
        );

        return this.boolType;
    }

    /**
     * INT
     * @param ctx the parse tree
     * @return Int
     */
    @Override
    public YAPLType visitInteger(YAPLParser.IntegerContext ctx) {
        this.addresses.put(ctx, ctx.getText());
        return this.intType;
    }

    /**
     * WHILE expr LOOP expr POOL
     * @param ctx the parse tree
     * @return Object type
     */
    @Override
    public YAPLType visitWhile(YAPLParser.WhileContext ctx) {
       // expr1 is not bool
        YAPLType whileExprType = visit(ctx.expr(0));

        String begin = genLabel();
        String BTrue = genLabel();
        String next = genLabel();
        String BFalse = next;

        this.generateQuad(
                "if",
                this.addresses.get(ctx.expr(0).getRuleContext()),
                "goto " + BTrue,
                null
        );
        // B false code
        this.generateQuad(
                "ifFalse",
                this.addresses.get(ctx.expr(0).getRuleContext()),
                "goto " + BFalse,
                null
        );
        this.generateQuad(begin + ":", null, null, null);
        this.generateQuad(BTrue + ":", null, null, null);
        visit(ctx.expr(1));
        this.generateQuad("goto " + begin, null, null, null);

        this.generateQuad(next + ":", null, null, null);

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

            //intermediate code
            // create temp
            YAPLSymbol temp = generateTemp(paramType);
            this.generateQuad(
                    "=",
                    this.addresses.get(ctx.expr(i).getRuleContext()),
                    this.getCodeFromSymbol(temp),
                    null
            );
            // param temp
            this.generateQuad("param", this.getCodeFromSymbol(temp), null, null);

            // asignar address a contexto
            if (i < ctx.expr().size()-1) {
                signature.append(paramType.getId()).append(", ");
            } else {
                signature.append(paramType.getId());
            }
        }
        signature.append(")");

        // get return type with signature
        YAPLType returnType = this.currentClass.getMethods().get(signature.toString()).getReturnType();

        // create return val temp
        YAPLSymbol returnValTemp = this.generateTemp(returnType);

        // set ctx address to return temp value
        this.addresses.put(ctx, this.getCodeFromSymbol(returnValTemp));

        // call intermediate code
        // returnTempVal = call funcID, param.length
        this.generateQuad(
                ",",
                "call " + this.currentClass.getId() + "." + ctx.ID().getText(),
                String.valueOf(ctx.expr().size()),
                this.getCodeFromSymbol(returnValTemp)
        );

        return returnType;
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
                this.addresses.put(ctx, this.addresses.get(ctx.expr(i).getRuleContext()));
            }
        }

        return returnType;
    }

    /**
     * expr op=('<'|'<='|'=') expr
     * @param ctx the parse tree
     * @return Bool if both expr can be compared.
     */
    @Override
    public YAPLType visitComp(YAPLParser.CompContext ctx) {
        YAPLType leftChild = visit(ctx.expr(0)); // left expr
        YAPLType rightChild = visit(ctx.expr(1)); // right expr

        // create temp
        YAPLSymbol temp = this.generateTemp(this.intType);

        this.generateQuad(
                ctx.op.getText(),
                this.addresses.get(ctx.expr1.getRuleContext()),
                this.addresses.get(ctx.expr2.getRuleContext()),
                this.getCodeFromSymbol(temp)
        );

        this.addresses.put(ctx, this.getCodeFromSymbol(temp));

        return this.boolType;
    }

    /**
     * '~' expr
     * ~ is complement of variables of type Int
     * @param ctx the parse tree
     * @return Int type
     */
    @Override
    public YAPLType visitIntComplement(YAPLParser.IntComplementContext ctx) {
        // bool
        YAPLType exprType = visit(ctx.expr());

        // intermediate code
        // create temp
        YAPLSymbol temp = generateTemp(exprType);

        this.generateQuad(
                "~",
                this.addresses.get(ctx.expr().getRuleContext()),
                null,
                this.getCodeFromSymbol(temp)
        );

        this.addresses.put(ctx, this.getCodeFromSymbol(temp));

        return this.intType; // ignore error
    }

    /**
     * 'not' expr
     * not is complement of variables of type Bool
     * @param ctx the parse tree
     * @return Boolean type
     */
    @Override
    public YAPLType visitBoolComplement(YAPLParser.BoolComplementContext ctx) {
        // bool
        YAPLType exprType = visit(ctx.expr());

        // intermediate code
        // create temp
        YAPLSymbol temp = this.generateTemp(exprType);

        this.generateQuad(
                "NOT",
                this.addresses.get(ctx.expr().getRuleContext()),
                null,
                this.getCodeFromSymbol(temp)
        );

        this.addresses.put(ctx, this.getCodeFromSymbol(temp));

        return this.boolType; // ignore error
    }

    /**
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

            YAPLType assignmentType = type;

            // validate that expression type match
            if (varDef.expr() != null) {
                assignmentType = visit(varDef.expr());
            }

            // add to symbol table
            YAPLType varType = this.types.getType(typeId);

            YAPLSymbol symbol = new YAPLSymbol(id, varType, varType.getWidth(), this.offsets.peek(), this.interCodeScope.peek());
            this.scopes.peek().add(symbol);
            this.offsets.push(this.offsets.pop() + varType.getWidth());

            this.generateQuad(
                    "=",
                    varType.getDefaultVal(),
                    this.getCodeFromSymbol(symbol),
                    null
            );

            // intermediate code
            if (varDef.expr() != null) {
                this.generateQuad(
                        "=",
                        this.addresses.get(varDef.expr().getRuleContext()),
                        this.getCodeFromSymbol(symbol),
                        null
                );
            }
        }

        YAPLType exprType = visit(ctx.expr());
        this.addresses.put(ctx, this.addresses.get(ctx.expr().getRuleContext()));

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

        // intermediate code
        this.addresses.put(ctx, this.getCodeFromSymbol(symbol));

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

            //intermediate code
            // create temp
            YAPLSymbol temp = this.generateTemp(paramType);

            this.generateQuad(
                    "=",
                    this.addresses.get(ctx.expr(i).getRuleContext()),
                    this.getCodeFromSymbol(temp),
                    null
            );
            // param temp
            this.generateQuad("param", this.getCodeFromSymbol(temp), null, null);

            if (i < ctx.expr().size()-1) {
                signature.append(paramType.getId()).append(", ");
            } else {
                signature.append(paramType.getId());
            }
        }
        signature.append(")");

        YAPLMethod method = exprType.getMethods().get(signature.toString());

        // get return type with signature
        YAPLType returnType = method.getReturnType();

        // create return val temp
        YAPLSymbol returnValTemp = this.generateTemp(returnType);

        // set ctx address to return temp value
        this.addresses.put(ctx, this.getCodeFromSymbol(returnValTemp));

        // call intermediate code
        // returnTempVal = call funcID, param.length
        this.generateQuad(
                ",",
                "call " + ctx.ID().getText(),
                String.valueOf(ctx.expr().size() - 1),
                this.getCodeFromSymbol(returnValTemp)
        );

        return method.getReturnType();
    }

    /**
     * IF expr THEN expr ELSE expr FI
     * @param ctx the parse tree
     * @return supertype of both expressions
     */
    @Override
    public YAPLType visitIfElse(YAPLParser.IfElseContext ctx) {

        String next = genLabel();

        // intermediate code
        String BTrue = this.genLabel();
        String BFalse = this.genLabel();

        visit(ctx.expr(0)); // visit B

        // B true code

        // create return val temp
        YAPLSymbol returnValTemp = this.generateTemp(this.objectType);

//        System.out.println(ctx.expr(0).getText());
        this.generateQuad("if", this.addresses.get(ctx.expr(0).getRuleContext()), "goto " + BTrue, null);
        // B false code
        this.generateQuad("ifFalse", this.addresses.get(ctx.expr(0).getRuleContext()), "goto " + BFalse, null);

        this.generateQuad(BTrue + ":", null, null, null);
        YAPLType thenType = visit(ctx.expr(1));
        this.generateQuad("=", this.addresses.get(ctx.expr(1).getRuleContext()), this.getCodeFromSymbol(returnValTemp),null);
        this.generateQuad("goto " + next, null, null, null);

        this.generateQuad(BFalse + ":", null, null, null);
        YAPLType elseType = visit(ctx.expr(2)); // visit Else expr
        this.generateQuad("=", this.addresses.get(ctx.expr(2).getRuleContext()), this.getCodeFromSymbol(returnValTemp),null);

        this.generateQuad(next + ":", null, null, null);


        // Assign return val to ctx address
        this.addresses.put(ctx, this.getCodeFromSymbol(returnValTemp));

        return thenType.commonAncestorWith(elseType);
    }

    /**
     * ID ASSIGN expr
     * @param ctx the parse tree
     * @return exprType
     */
    @Override
    public YAPLType visitAssign(YAPLParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        YAPLType exprType = visit(ctx.expr());

        YAPLSymbol symbol = getSymbol(id); // get symbol from first symtable

        // intermediate code
        this.generateQuad(
                "=",
                this.addresses.get(ctx.expr().getRuleContext()),
                this.getCodeFromSymbol(symbol),
                null
        );

        this.addresses.put(ctx, this.getCodeFromSymbol(symbol));

        return exprType;
    }
}
