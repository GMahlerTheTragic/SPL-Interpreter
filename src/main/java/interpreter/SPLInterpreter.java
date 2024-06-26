package interpreter;

import frontend.SPLLexer;
import frontend.SPLParser;
import frontend.SPLParser.ProgramContext;
import interpreter.visitor.SPLVisitorImpl;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;


public class SPLInterpreter {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Fatal Error: No input file specified - terminating");
            System.exit(1);
        }

        // Read in the program from file
        String filePath = args[0];
        String programAsString = Utils.readFileToString(filePath);

        // Parse the program
        assert programAsString != null;
        SPLLexer lexer = new SPLLexer(CharStreams.fromString(programAsString));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SPLParser parser = new SPLParser(tokens);

        // Get the root node of the parse tree
        ProgramContext tree = parser.program();

        // Create an instance of the interpreter
        SPLVisitorImpl interpreter = new SPLVisitorImpl(tokens);

        // Traverse the parse tree starting from the root node (which is program)
        interpreter.visitProgram(tree);
    }
}
