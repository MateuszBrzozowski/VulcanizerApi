package pl.mbrzozowski.vulcanizer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserWasNotFoundException extends RuntimeException {
    public UserWasNotFoundException(String s) {
        super(s);
    }
}
