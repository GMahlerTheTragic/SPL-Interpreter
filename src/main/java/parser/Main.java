package parser;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import parser.SPLParser.ProgramContext;

public class Main {

    public static String readFileToString(String filePath) {
        Path path = Paths.get(System.getProperty("user.dir") + filePath);
        try {
            byte[] bytes = Files.readAllBytes(path);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String filePath = "/spl-sample-programms/Mid.spl";
        String expression = readFileToString(filePath);
        System.out.println(expression);

        SPLLexer lexer = new SPLLexer(CharStreams.fromString(expression));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SPLParser parser = new SPLParser(tokens);
        ProgramContext tree = parser.program();
        System.out.println(tree.toStringTree(parser));

        // Create an instance of the interpreter
        SPLBaseVisitor interpreter = new SPLBaseVisitor(tokens);

        // Traverse the parse tree and execute interpreter logic
        interpreter.visitProgram(tree);
        
    }
}
