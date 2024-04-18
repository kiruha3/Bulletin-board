package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class ImageCanNotBeReadException extends RuntimeException {
    public ImageCanNotBeReadException() {
        super();
    }

    public ImageCanNotBeReadException(String message) {
        super(message);
    }
}
