package app.resketchware.builder.exceptions;

public class CompilationFailedException extends Exception {

    public CompilationFailedException(Exception exception) {
        super(exception);
    }

    public CompilationFailedException(String message) {
        super(message);
    }

    public CompilationFailedException(String message, Throwable th) {
        super(message, th);
    }
}