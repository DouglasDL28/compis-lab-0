package compis.lab0;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.Paragraph;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.collection.ListModification;

public class UIMain extends Application {

    private static final String[] KEYWORDS = new String[] {
            "inherits", "Int", "Bool", "if", "IF",
            "case", "while", "String", "class", "WHILE",
            "SELF_TYPE", "self", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while"
    };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/"   // for whole text processing (text blocks)
            + "|" + "/\\*[^\\v]*" + "|" + "^\\h*\\*([^\\v]*|/)";  // for visible paragraph processing (line by line)

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    private static final String sampleCode = String.join("\n", new String[] {
            "class Results {",
            "",
            "    ress: Int; ",
            "",
            "    get_ress() : Int {",
            "        ress",
            "    };",
            "",
            "    set_res(i: Int) : SELF_TYPE {",
            "        {",
            "            ress <- i;",
            "            self;",
            "        }",
            "    };",
            "};",
            "",
            "",
            "class Factorial {",
            "    factorial(n: Int) : Int {",
            "      	 if n=0 then 0 else",
            "         if n=1 then 1 else",
            "        	n*factorial(n-1)",
            "         fi fi",
            "    };",
            "};",
            "",
            "class Div inherits Factorial {",
            "    div(n: Int, o: Int) : Int {",
            "        n/o",
            "    };",
            "};",
            "",
            "class SumSub inherits Div {",
            "",
            "    sum(n: Int, o: Int) : Int {",
            "        n + o ",
            "    };",
            "    sub(n: Int, o: Int) : Int {",
            "        n - o",
            "    };",
            "};",
            "",
            "class Calculator inherits SumSub {",
            "    mul(n: Int, o: Int) : Int {",
            "        n*o",
            "    };",
            "};",
            "",
            "",
            "class Main inherits IO {",
            "    a : Results;",
            "    calc: Calculator;",
            "",
            "    main() : SELF_TYPE {",
            "        {",
            "            a.set_res(calc.mul(5,4));",
            "            out_int(a.get_ress());",
            "            a.set_res(calc@SumSub.sum(5,6));",
            "            out_int(a.get_ress());",
            "            a.set_res(calc@SumSub.sub(5, calc@SumSub.sum(5,6)));",
            "            out_int(a.get_ress());",
            "            a.set_res(calc@Factorial.factorial(5));",
            "            out_int(a.get_ress());",
            "            self;",
            "        }",
            "    } ;",
            "",
            "};"
    });

    private Button compileBtn;
    private TextArea console;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        CodeArea codeArea = new CodeArea();

        // add line numbers to the left of area
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.setContextMenu( new DefaultContextMenu() );
/*
        // recompute the syntax highlighting for all text, 500 ms after user stops editing area
        // Note that this shows how it can be done but is not recommended for production with
        // large files as it does a full scan of ALL the text every time there is a change !
        Subscription cleanupWhenNoLongerNeedIt = codeArea
                // plain changes = ignore style changes that are emitted when syntax highlighting is reapplied
                // multi plain changes = save computation by not rerunning the code multiple times
                //   when making multiple changes (e.g. renaming a method at multiple parts in file)
                .multiPlainChanges()
                // do not emit an event until 500 ms have passed since the last emission of previous stream
                .successionEnds(Duration.ofMillis(500))
                // run the following code block when previous stream emits an event
                .subscribe(ignore -> codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText())));
        // when no longer need syntax highlighting and wish to clean up memory leaks
        // run: `cleanupWhenNoLongerNeedIt.unsubscribe();`
*/
        // recompute syntax highlighting only for visible paragraph changes
        // Note that this shows how it can be done but is not recommended for production where multi-
        // line syntax requirements are needed, like comment blocks without a leading * on each line.
        codeArea.getVisibleParagraphs().addModificationObserver
                (
                        new VisibleParagraphStyler<>( codeArea, this::computeHighlighting )
                );

        // auto-indent: insert previous line's indents on enter
        final Pattern whiteSpace = Pattern.compile( "^\\s+" );
        codeArea.addEventHandler( KeyEvent.KEY_PRESSED, KE ->
        {
            if ( KE.getCode() == KeyCode.ENTER ) {
                int caretPosition = codeArea.getCaretPosition();
                int currentParagraph = codeArea.getCurrentParagraph();
                Matcher m0 = whiteSpace.matcher( codeArea.getParagraph( currentParagraph-1 ).getSegments().get( 0 ) );
                if ( m0.find() ) Platform.runLater( () -> codeArea.insertText( caretPosition, m0.group() ) );
            }
        });


        codeArea.replaceText(0, 0, sampleCode);

        compileBtn = new Button();
        compileBtn.setText("Compile");
        compileBtn.setOnAction(actionEvent -> {
            InputStream is = new ByteArrayInputStream(codeArea.getText().getBytes(StandardCharsets.UTF_8));

            try {
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

                    StringBuilder sb = new StringBuilder();
                    for (YAPLSemError error : errors) {
                        sb.append(error.toString()).append("\n");
                    }
                    sb.append("\n Detected ").append(errors.size()).append(" errors.");

                    this.console.setText(sb.toString());
                    System.out.println("Detected " + errors.size() + " errors.");
                } else {
                    System.out.println("\n");
                    this.console.setText("Compiled successfully!");
                    System.out.println("Compiled successfully!");
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        this.console = new TextArea();
        this.console.setEditable(false);
        this.console.setMinHeight(700);
        this.console.setMaxHeight(800);
        this.console.setMinWidth(20);
        this.console.setMaxWidth(500);

        BorderPane layout = new BorderPane();
        layout.setCenter(new VirtualizedScrollPane<>(codeArea));

        layout.setTop(this.compileBtn);
        layout.setRight(this.console);


        Scene scene = new Scene(layout, 1000, 800);

        scene.getStylesheets().add(UIAsync.class.getResource("yapl-keywords.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("YAPL Code");
        primaryStage.show();
    }

    private StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("STRING") != null ? "string" :
                                                                    matcher.group("COMMENT") != null ? "comment" :
                                                                            null; /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private class VisibleParagraphStyler<PS, SEG, S> implements Consumer<ListModification<? extends Paragraph<PS, SEG, S>>>
    {
        private final GenericStyledArea<PS, SEG, S> area;
        private final Function<String,StyleSpans<S>> computeStyles;
        private int prevParagraph, prevTextLength;

        public VisibleParagraphStyler( GenericStyledArea<PS, SEG, S> area, Function<String,StyleSpans<S>> computeStyles )
        {
            this.computeStyles = computeStyles;
            this.area = area;
        }

        @Override
        public void accept( ListModification<? extends Paragraph<PS, SEG, S>> lm )
        {
            if ( lm.getAddedSize() > 0 )
            {
                int paragraph = Math.min( area.firstVisibleParToAllParIndex() + lm.getFrom(), area.getParagraphs().size()-1 );
                String text = area.getText( paragraph, 0, paragraph, area.getParagraphLength( paragraph ) );

                if ( paragraph != prevParagraph || text.length() != prevTextLength )
                {
                    int startPos = area.getAbsolutePosition( paragraph, 0 );
                    Platform.runLater( () -> area.setStyleSpans( startPos, computeStyles.apply( text ) ) );
                    prevTextLength = text.length();
                    prevParagraph = paragraph;
                }
            }
        }
    }

    private class DefaultContextMenu extends ContextMenu
    {
        private MenuItem fold, unfold, print;

        public DefaultContextMenu()
        {
            fold = new MenuItem( "Fold selected text" );
            fold.setOnAction( AE -> { hide(); fold(); } );

            unfold = new MenuItem( "Unfold from cursor" );
            unfold.setOnAction( AE -> { hide(); unfold(); } );

            print = new MenuItem( "Print" );
            print.setOnAction( AE -> { hide(); print(); } );

            getItems().addAll( fold, unfold, print );
        }

        /**
         * Folds multiple lines of selected text, only showing the first line and hiding the rest.
         */
        private void fold() {
            ((CodeArea) getOwnerNode()).foldSelectedParagraphs();
        }

        /**
         * Unfold the CURRENT line/paragraph if it has a fold.
         */
        private void unfold() {
            CodeArea area = (CodeArea) getOwnerNode();
            area.unfoldParagraphs( area.getCurrentParagraph() );
        }

        private void print() {
            System.out.println( ((CodeArea) getOwnerNode()).getText() );
        }
    }
}