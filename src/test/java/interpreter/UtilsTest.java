package interpreter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void testFormatErrorMessage() {
        int line = 5;
        int posInLine = 10;
        String message = "Syntax error";
        String expected = "Error at [5, 10]: Syntax error";
        String result = Utils.formatErrorMessage(line, posInLine, message);
        assertEquals(expected, result);
    }

    @Test
    void testReadFileToStringWithExistingFile() {
        String filePath = "/src/main/resources/spl-sample-programs/Fibonacci.spl";
        String expectedContent = "// Print the first n Fibonacci numbers\n" + //
                "\n" + //
                "// Input\n" + //
                "var n = 10;\n" + //
                "\n" + //
                "// Here we go\n" + //
                "var a = 0; \n" + //
                "var b = 1; \n" + //
                "\n" + //
                "var i = 1;\n" + //
                "while (i <= n) {\n" + //
                "\tprint a;\n" + //
                "\tvar next = a + b;\n" + //
                "\ta = b;\n" + //
                "\tb = next;\n" + //
                "\ti = i + 1;\n" + //
                "} ";

        String result = Utils.readFileToString(filePath);
        assertNotNull(result);
        assertEquals(expectedContent, result);
    }

    @Test
    void testReadFileToStringWithNonExistingFile() {
        String filePath = "/path/to/nonexistent/file.txt";
        String result = Utils.readFileToString(filePath);
        assertNull(result);
    }
}

