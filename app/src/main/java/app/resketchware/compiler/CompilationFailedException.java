package app.resketchware.compiler;

public class CompilationFailedException extends Exception {

    public CompilationFailedException() {
        this("Compilation failed");
    }

    public CompilationFailedException(String message) {
        super(message);
    }

    public CompilationFailedException(String message, Throwable th) {
        super(message, th);
    }
}