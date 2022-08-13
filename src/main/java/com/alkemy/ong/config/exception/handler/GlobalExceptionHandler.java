package com.alkemy.ong.config.exception.handler;

import com.alkemy.ong.config.exception.ConflictException;
import com.alkemy.ong.config.exception.NotFoundException;
import com.alkemy.ong.config.exception.error.ErrorCode;
import com.alkemy.ong.config.exception.error.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public final class GlobalExceptionHandler extends AbstractExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorDetails> handleNotFound(NotFoundException ex) {

        ErrorDetails error = ErrorDetails.builder()
                .code(ErrorCode.RESOURCE_NOT_FOUND)
                .detail("The resource with id %s is not found.".formatted(ex.getResourceId()))
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ConflictException.class)
    private ResponseEntity<ErrorDetails> handleConflict(ConflictException ex) {

        ErrorDetails error = ErrorDetails.builder()
                .code(ErrorCode.RESOURCE_ALREADY_EXISTS)
                .detail(ex.getConflictMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
