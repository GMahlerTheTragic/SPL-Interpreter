package interpreter;



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;



public class SPLInterpreterTest {
    
    @Test
    public void testFibonacci() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        String[] pathToProgram = {"/src/main/resources/spl-sample-programms/Fibonacci.spl"};
        SPLInterpreter.main(pathToProgram);
        // Restore System.out
        System.setOut(originalOut);

        // Get the captured output
        String output = outputStream.toString().trim();

        // Assert the expected output
        String expectedOutput = "0.0\n" + //
                "1.0\n" + //
                "1.0\n" + //
                "2.0\n" + //
                "3.0\n" + //
                "5.0\n" + //
                "8.0\n" + //
                "13.0\n" + //
                "21.0\n" + //
                "34.0";
        assertEquals(expectedOutput, output);

    }

    @Test
    public void testEuclidean() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        String[] pathToProgram = {"/src/main/resources/spl-sample-programms/Euclidean.spl"};
        SPLInterpreter.main(pathToProgram);
        // Restore System.out
        System.setOut(originalOut);

        // Get the captured output
        String output = outputStream.toString().trim();

        // Assert the expected output
        String expectedOutput = "4.0";
        assertEquals(expectedOutput, output);

    }

    @Test
    public void testMid() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        String[] pathToProgram = {"/src/main/resources/spl-sample-programms/Mid.spl"};
        SPLInterpreter.main(pathToProgram);
        // Restore System.out
        System.setOut(originalOut);

        // Get the captured output
        String output = outputStream.toString().trim();

        // Assert the expected output
        String expectedOutput = "5.0";
        assertEquals(expectedOutput, output);

    }

    @Test
    public void testSample() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        String[] pathToProgram = {"/src/main/resources/spl-sample-programms/sample.spl"};
        SPLInterpreter.main(pathToProgram);
        // Restore System.out
        System.setOut(originalOut);

        // Get the captured output
        String output = outputStream.toString().trim();

        // Assert the expected output
        String expectedOutput = "Basic looping: ok\n" + //
                "Shadowing: ok\n" + //
                "Outer scope: ok\n" + //
                "Logic and: ok\n" + //
                "Logic or: ok\n" + //
                "Number + String: ok";
        assertEquals(expectedOutput, output);

    }
}
