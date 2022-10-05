package compis.lab0;

import java.util.List;

/**
 * YAPL types visitor. Defines
 */
public class YAPLTypesVisitor extends YAPLBaseVisitor<YAPLType> {
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
    public YAPLTypesVisitor(YAPLTypesTable types, List<YAPLSemError> errors) {
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

        // validate new type's existence
        if (this.types.containsType(ctx.classId.getText())) {
            YAPLSemError error = new YAPLSemError(
                    ctx.classId.getLine(),
                    ctx.classId.getCharPositionInLine(),
                    "Class " + ctx.classId.getText() + " is already defined."
            );
            System.out.println(error);
            this.errors.add(error);

        } else { // can add new type

            YAPLType parent = objectType; // default parent

            // inheritance defined
            if (ctx.parentId != null) {
                String parentId = ctx.parentId.getText();

                parent = this.types.getType(ctx.parentId.getText());

                // validate parent type
                if (!this.types.containsType(parentId)) {
                    YAPLSemError error = new YAPLSemError(
                            ctx.parentId.getLine(),
                            ctx.parentId.getCharPositionInLine(),
                            "Parent type " + ctx.parentId.getText() + " not defined."
                    );
                    System.out.println(error);
                    errors.add(error);
                    parent = this.objectType;
                }

                // validate inheritance from primitives
                if (isPrimitive(parentId)) {
                    YAPLSemError error = new YAPLSemError(
                            ctx.parentId.getLine(),
                            ctx.parentId.getCharPositionInLine(),
                            "Can't inherit from primitive type."
                    );
                    System.out.println(error);
                    errors.add(error);
                    parent = this.objectType;
                }

            }

            YAPLType newClass = new YAPLType(ctx.classId.getText(), parent, parent.getDepth()+1);
            this.types.addType(newClass); // add class

            this.types.addType(newClass);
            this.currentClass = newClass;

            for (YAPLParser.FuncDefContext funcDef : ctx.funcDef()) {
                visit(funcDef);
            }

            this.currentClass = null; // return class to null

            return this.objectType;
        }

        return this.objectType;
    }
}
