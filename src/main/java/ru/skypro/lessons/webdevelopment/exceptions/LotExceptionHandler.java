package ru.skypro.lessons.webdevelopment.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice
public class LotExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(LotExceptionHandler.class);

    @ExceptionHandler(value = {SQLException.class, IOException.class, LotNotStartedException.class})
    public ResponseEntity<?> badRequest(Exception exception) {
        LOG.error(exception.getMessage(), exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = LotNotFoundException.class)
    public ResponseEntity<?> notFound(Exception exception) {
        LOG.error(exception.getMessage(), exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
