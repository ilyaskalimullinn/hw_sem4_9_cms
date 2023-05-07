package ru.kpfu.itis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TitleParseException extends IllegalArgumentException {
    public TitleParseException() {
    }

    public TitleParseException(String s) {
        super(s);
    }

    public TitleParseException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TitleParseException(Throwable throwable) {
        super(throwable);
    }
}
