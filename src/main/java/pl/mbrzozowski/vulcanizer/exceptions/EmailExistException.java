package pl.mbrzozowski.vulcanizer.exceptions;

public class EmailExistException extends CustomRuntimeExceptions {

    public EmailExistException(String message) {
        super(message);
    }

    public EmailExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailExistException(Throwable cause) {
        super(cause);
    }
}