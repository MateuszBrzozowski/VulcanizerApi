package pl.mbrzozowski.vulcanizer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NullParameterException extends RuntimeException {

    public NullParameterException(String message) {
        super(message);
    }
}
