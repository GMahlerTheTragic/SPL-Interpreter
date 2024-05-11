package interpreter;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SPLInterpreterTest {

    @Test
    void testFibonacci() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        String[] pathToProgram = {"/src/main/resources/spl-sample-programs/Fibonacci.spl"};
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
    void testEuclidean() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        String[] pathToProgram = {"/src/main/resources/spl-sample-programs/Euclidean.spl"};
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
    void testMid() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        String[] pathToProgram = {"/src/main/resources/spl-sample-programs/Mid.spl"};
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
    void testSample() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        String[] pathToProgram = {"/src/main/resources/spl-sample-programs/sample.spl"};
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
