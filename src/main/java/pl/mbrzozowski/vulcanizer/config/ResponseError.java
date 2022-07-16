package pl.mbrzozowski.vulcanizer.config;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseError {
    private final String timestamp;
    private final String status;
    private final String error;
    private final String message;

    public ResponseError(final String status,
                         final String error,
                         final String message) {
        this.timestamp = LocalDateTime.now().toString();
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
