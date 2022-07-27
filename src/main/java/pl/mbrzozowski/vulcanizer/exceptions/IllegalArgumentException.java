package pl.mbrzozowski.vulcanizer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalArgumentException extends CustomRuntimeExceptions {

    public IllegalArgumentException(String message) {
        super(message);
    }

    public IllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalArgumentException(Throwable cause) {
        super(cause);
    }
}
