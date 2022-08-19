import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;


import java.io.FileInputStream;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws Exception {
//        YAPLScopesStack scopes = new YAPLScopesStack();

        String inputFile = "/Users/douglasjr/Documents/DouglasJr/UVG/semestre10/Compiladores/pruebas_YAPL/hello_world.cl";
        if ( args.length>0 ) inputFile = args[0];
        InputStream is = System.in;
        if ( inputFile!=null ) is = new FileInputStream(inputFile);
        ANTLRInputStream input = new ANTLRInputStream(is);

        YAPLLexer lexer = new YAPLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        YAPLParser parser = new YAPLParser(tokens);

        ParseTree tree = parser.program(); // parse

        YAPLTypesTable types = new YAPLTypesTable();
        YAPLTypesVisitor typesVisitor = new YAPLTypesVisitor(types);
        typesVisitor.visit(tree);

        System.out.println("Size:" + types.size());

        System.out.println(types.containsType("Test"));

        YAPLSemanticVisitor semVisitor = new YAPLSemanticVisitor(types);



        semVisitor.visit(tree);

//        YAPLLexer lexer = new YAPLLexer(CharStreams.fromFileName("../../../pruebas_YAPL/hello_world.cl"));
//        YAPLParser parser = new YAPLParser(new CommonTokenStream(lexer));
//        Integer sum = new SumVisitor().visit(parser.YAPL());

//        System.out.println("sum=" + sum);
    }
}