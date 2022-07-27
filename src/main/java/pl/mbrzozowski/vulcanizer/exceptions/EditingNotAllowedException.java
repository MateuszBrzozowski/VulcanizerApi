package pl.mbrzozowski.vulcanizer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class EditingNotAllowedException extends CustomRuntimeExceptions{

    public EditingNotAllowedException(String message) {
        super(message);
    }

    public EditingNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public EditingNotAllowedException(Throwable cause) {
        super(cause);
    }
}
