package interpreter.exceptions;


/**
 * Variable undefined error.
 */
public class VariableUndefinedException extends RuntimeException {

    public VariableUndefinedException(String message) {
        super(message);
    }

}
