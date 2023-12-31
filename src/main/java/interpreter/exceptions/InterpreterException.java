package interpreter.exceptions;

import interpreter.Utils;

// Exception to be thrown by the Interpreter at runtime, if erroneous language use is detected
public abstract class InterpreterException extends RuntimeException {

    private int line;
    private int posInLine;

    public InterpreterException(String message) {
        super(message);
    }

    public void setLocation(int line, int posInLine) {
        this.line = line;
        this.posInLine = posInLine;
    }

    @Override
    public String getMessage() {
        return Utils.formatErrorMessage(line, posInLine, super.getMessage());
    }
}
