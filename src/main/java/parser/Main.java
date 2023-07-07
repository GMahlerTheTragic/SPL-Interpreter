package parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Main {
    public static void main(String[] args) {
        String expression = "a = 1;";

        SPLLexer lexer = new SPLLexer(CharStreams.fromString(expression));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SPLParser parser = new SPLParser(tokens);
        ParseTree tree = parser.program();

        System.out.println("Parse tree: " + tree.toStringTree(parser));
    }
}
