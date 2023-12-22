package interpreter;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * format error message to show line number etc. for an Interpreter Error
 */
public class Utils {

    public static String formatErrorMessage(int line, int posInLine, String message) {
        return "Error at [" + line + ", " + posInLine + "]: " + message;
    }

    public static String readFileToString(String filePath) {
        Path path = Paths.get(System.getProperty("user.dir") + filePath);
        try {
            byte[] bytes = Files.readAllBytes(path);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println(e)
            return null;
        }
    }

}
