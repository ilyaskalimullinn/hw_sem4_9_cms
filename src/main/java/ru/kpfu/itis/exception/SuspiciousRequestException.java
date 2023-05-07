package ru.kpfu.itis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SuspiciousRequestException extends RuntimeException{
    public SuspiciousRequestException() {
    }

    public SuspiciousRequestException(String message) {
        super(message);
    }

    public SuspiciousRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public SuspiciousRequestException(Throwable cause) {
        super(cause);
    }

    public SuspiciousRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
