package interpreter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import frontend.SPLLexer;
import frontend.SPLParser;
import frontend.SPLParser.ProgramContext;
import interpreter.visitor.SPLVisitorImpl;

public class SPLInterpreter {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Warning: No input file specified - terminating");
        }
        String filePath = args[0];
        String expression = Utils.readFileToString(filePath);
        SPLLexer lexer = new SPLLexer(CharStreams.fromString(expression));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SPLParser parser = new SPLParser(tokens);
        ProgramContext tree = parser.program();

        // Create an instance of the interpreter
        SPLVisitorImpl interpreter = new SPLVisitorImpl(tokens);

        // Traverse the parse tree and execute interpreter logic
        interpreter.visitProgram(tree);

    }
}
