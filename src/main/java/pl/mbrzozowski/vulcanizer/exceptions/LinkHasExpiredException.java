package pl.mbrzozowski.vulcanizer.exceptions;

public class LinkHasExpiredException extends RuntimeException {

    public LinkHasExpiredException(String message) {
        super(message);
    }
}
