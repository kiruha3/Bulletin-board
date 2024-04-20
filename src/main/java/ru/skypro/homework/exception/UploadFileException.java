package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UploadFileException extends RuntimeException {
    public UploadFileException() {
        super("Проблемы при загрузке изображения");
    }
}
