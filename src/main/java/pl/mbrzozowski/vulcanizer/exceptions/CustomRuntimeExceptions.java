package pl.mbrzozowski.vulcanizer.exceptions;

public abstract class CustomRuntimeExceptions extends RuntimeException{

    public CustomRuntimeExceptions(String message) {
        super(message);
    }

    public CustomRuntimeExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomRuntimeExceptions(Throwable cause) {
        super(cause);
    }
}
