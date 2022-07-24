package pl.mbrzozowski.vulcanizer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class EditingNotAllowedException extends RuntimeException{


    public EditingNotAllowedException(String s) {
        super(s);
    }

    public EditingNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public EditingNotAllowedException(Throwable cause) {
        super(cause);
    }
}
