package net.spike;

import com.google.common.base.Predicates;
import com.google.javascript.jscomp.CodePrinter;
import com.google.javascript.jscomp.NodeUtil;
import com.google.javascript.jscomp.parsing.Config;
import com.google.javascript.jscomp.parsing.ParserRunner;
import com.google.javascript.jscomp.SourceFile;
import com.google.javascript.rhino.ErrorReporter;
import com.google.javascript.rhino.Node;
import com.google.javascript.rhino.SimpleErrorReporter;
import com.google.javascript.rhino.Token;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        Config config = ParserRunner.createConfig(Config.LanguageMode.ECMASCRIPT6, null, Config.StrictMode.STRICT);
        ErrorReporter errorReporter = new SimpleErrorReporter();

        // parse a source file into an ast.
        InputStream stream = App.class.getClassLoader().getResourceAsStream
                ("login-view" +
            ".js");
        //SourceFile sourceFile = SourceFile.fromFile

            //("/home/mjvesa/projects/designer/git/p3-test/src/main/resources" +
                      //"/login-view.js");
        try {
            SourceFile sourceFile = SourceFile.fromInputStream("login-view" +
                    ".js", stream,  Charset.defaultCharset());
            ParserRunner.ParseResult parseResult = ParserRunner
                    .parse(sourceFile, sourceFile.getCode(), config, errorReporter);

            // run the visitor on the ast to extract the needed values.
            DependencyVisitor visitor = new DependencyVisitor();
            NodeUtil.visitPreOrder(parseResult.ast, visitor, Predicates.<Node>alwaysTrue());

            // Print out imports
            System.out.println("Imports:");
            for (String str : visitor.importStrings) {
                System.out.println(str);
            }

            // Print out template
            System.out.println("\nTemplate:");
            System.out.println(visitor.templateTextNode.getString());

            // Remove imports. The first child is the module, and the imports
            // are the children of that.
            for (Node node : visitor.importNodes) {
                parseResult.ast.getFirstChild().removeChild(node);
            }

            // Replace template content.
            visitor.templateTextNode.replaceWith(Node.newString
                    ("<html><title>I am your " +
                            "replacement</title><body></body></html>"));

            // Prints component source without imports and with the changed
            // template
            String javascript = new CodePrinter.Builder(parseResult.ast).build();

            System.out.println(javascript);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
