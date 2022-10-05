package compis.lab0;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YAPLMethodsVisitor extends YAPLBaseVisitor<YAPLType> {
    private final YAPLTypesTable types;
    private final List<YAPLSemError> errors;
    private YAPLType currentClass = null;
    private final YAPLType objectType;
    private final YAPLType stringType;
    private final YAPLType intType;
    private final YAPLType boolType;

    public boolean isPrimitive(String id) {
        return switch (id) {
            case "Bool", "Int", "String" -> true;
            default -> false;
        };
    }
    public YAPLMethodsVisitor(YAPLTypesTable types, List<YAPLSemError> errors) {
        this.types = types;
        this.errors = errors;

        // Predefined types
        this.objectType = this.types.getType("Object");
        this.stringType = this.types.getType("String");
        this.intType = this.types.getType("Int");
        this.boolType = this.types.getType("Bool");
    }

    @Override
    public YAPLType visitProgram(YAPLParser.ProgramContext ctx) {
        return super.visitProgram(ctx);
    }

    @Override
    public YAPLType visitClassDef(YAPLParser.ClassDefContext ctx) {
        this.currentClass = this.types.getType(ctx.classId.getText());

        for (Map.Entry<String, YAPLMethod> method : this.currentClass.getParent().getMethods().entrySet()) {
            YAPLMethod newMethod = new YAPLMethod(method.getValue().getId(), method.getValue().getReturnType(), method.getValue().getParams(), false);
            // validate self_type
            if (method.getValue().returnsSelfType()) {
                newMethod.setReturnType(this.currentClass);
                newMethod.setReturnsSelfType(true);
            }
            this.currentClass.getMethods().put(method.getKey(), newMethod);
        }

        for (YAPLParser.FuncDefContext funcDef : ctx.funcDef()) {
            visit(funcDef);
        }

        this.currentClass = null; // return class to null

        return this.objectType;
    }

    @Override
    public YAPLType visitFuncDef(YAPLParser.FuncDefContext ctx) {
        YAPLType returnType = this.types.getType(ctx.TYPE().getText());
        boolean returnsSelfType = false;

        // handle self type
        // should return type that calls the function
        if (ctx.TYPE().getText().equals("SELF_TYPE")) {
            returnType = this.currentClass;
            returnsSelfType = true;
        }

        if (returnType == null) {
            returnType = this.objectType;

            YAPLSemError error = new YAPLSemError(
                    ctx.TYPE().getSymbol().getLine(),
                    ctx.TYPE().getSymbol().getCharPositionInLine(),
                    "Return type in " + ctx.ID().getText() + " not defined."
            );
            System.out.println(error);
            this.errors.add(error);
        }


        // Create params list with formals
        List<YAPLType> params = new ArrayList<>();
        String methodSignature = ctx.ID().getText() + "(";
        for (int i=0; i<ctx.formal().size(); i++) {
            YAPLParser.FormalContext formal = ctx.formal(i);

            String id = formal.ID().getText();
            String typeId = formal.TYPE().getText();

            if (i == ctx.formal().size()-1) {
                methodSignature = methodSignature + typeId;
            } else {
                methodSignature = methodSignature + typeId + ", ";
            }

            YAPLType paramReturnType = this.types.getType(typeId);

            if (paramReturnType == null) {
                YAPLSemError error = new YAPLSemError(
                        formal.start.getLine(),
                        formal.start.getCharPositionInLine(),
                        "Return type " + typeId + " in " + id + " not defined."
                );
                System.out.println(error);
                this.errors.add(error);

                paramReturnType = this.objectType;
            }

            params.add(paramReturnType);
        }
        methodSignature = methodSignature + ")";

        YAPLMethod newMethod = new YAPLMethod(ctx.ID().getText(), returnType, params, returnsSelfType);

        this.currentClass.getMethods().put(methodSignature, newMethod);

        return this.objectType;
    }
}
