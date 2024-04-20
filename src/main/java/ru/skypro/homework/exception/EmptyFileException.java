package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class EmptyFileException extends RuntimeException {
    public EmptyFileException() {
        super("Проверьте свой файл изображения и повторите попытку");
    }
}
