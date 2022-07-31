package pl.mbrzozowski.vulcanizer.exceptions;

public class AccountNotActiveException extends RuntimeException {

    public AccountNotActiveException(String message) {
        super(message);
    }
}
