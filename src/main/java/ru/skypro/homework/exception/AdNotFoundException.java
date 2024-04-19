package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class AdNotFoundException extends RuntimeException {
    public AdNotFoundException() {
        super();
    }

    public AdNotFoundException(String message) {
        super(message);
    }

    public AdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdNotFoundException(Throwable cause) {
        super(cause);
    }

    protected AdNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    //private final int id;
    //@Override
    //public String getMessage() {
    //  return "Объявление с таким id: " + id + " не найдено";}
}
