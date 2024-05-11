package interpreter.exceptions;


/**
 * Type mismatch error.
 */
public class TypeException extends RuntimeException {

    public TypeException(String message) {
        super(message);
    }

}
