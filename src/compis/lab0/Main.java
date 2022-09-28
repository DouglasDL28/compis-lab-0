package compis.lab0;

import javafx.application.Application;
import javafx.stage.Stage;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {
//    @Override
//    public void start(Stage stage) throws IOException {
//        stage.setTitle("Hello!");
//        stage.show();
//    }

    public static void main(String[] args) throws Exception {
//        launch();
//        YAPLScopesStack scopes = new YAPLScopesStack();

        String inputFile = "/Users/douglasjr/Documents/DouglasJr/UVG/semestre10/Compiladores/pruebas_YAPL/recur.cl";
        if ( args.length>0 ) inputFile = args[0];
        InputStream is = System.in;
        if ( inputFile!=null ) is = new FileInputStream(inputFile);
        ANTLRInputStream input = new ANTLRInputStream(is);

        YAPLLexer lexer = new YAPLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        YAPLParser parser = new YAPLParser(tokens);

        ParseTree tree = parser.program(); // parse

        YAPLTypesTable types = new YAPLTypesTable();
        List<YAPLSemError> errors = new ArrayList<>();

        YAPLTypesVisitor typesVisitor = new YAPLTypesVisitor(types, errors);
        typesVisitor.visit(tree);
        System.out.println("\n");

        YAPLMethodsVisitor methodsVisitor = new YAPLMethodsVisitor(types, errors);
        methodsVisitor.visit(tree);
        System.out.println("\n");

        YAPLSemanticVisitor semVisitor = new YAPLSemanticVisitor(types, errors);
        semVisitor.visit(tree);

        if (errors.size() > 0) {
            System.out.println("Detected " + errors.size() + " errors.");
        } else {
            System.out.println("\n");
            System.out.println("Compiled successfully!");
        }

    }
}