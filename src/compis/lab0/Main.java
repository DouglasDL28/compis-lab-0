package compis.lab0;

import javafx.application.Application;
import javafx.stage.Stage;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        String inputFile = "/Users/douglasjr/Documents/DouglasJr/UVG/semestre10/Compiladores/pruebas_YAPL/cool.cl";
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
        List<Quad> intermediateCode = new ArrayList<>();

        YAPLTypesVisitor typesVisitor = new YAPLTypesVisitor(types, errors);
        typesVisitor.visit(tree);

        YAPLMethodsVisitor methodsVisitor = new YAPLMethodsVisitor(types, errors);
        methodsVisitor.visit(tree);

        YAPLSemanticVisitor semVisitor = new YAPLSemanticVisitor(types, errors);
        semVisitor.visit(tree);

        if (errors.size() > 0) {
            System.out.println("Detected " + errors.size() + " errors.");
        } else {
            YAPLIntermediateCodeVisitor intermediateCodeVisitor = new YAPLIntermediateCodeVisitor(types, intermediateCode);
            intermediateCodeVisitor.visit(tree);

            System.out.println("Compiled successfully!\n");
        }

//        System.out.println("Intermediate code:\n");
//        for (Quad q: intermediateCode) {
//            System.out.println(q);
//        }

    }
}