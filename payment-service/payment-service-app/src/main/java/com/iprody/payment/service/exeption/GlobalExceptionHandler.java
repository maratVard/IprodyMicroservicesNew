package com.iprody.payment.service.exeption;

import com.iprody.payment.service.services.ErrorDto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
        @ExceptionHandler(EntityNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public ErrorDto handleNotFound(EntityNotFoundException ex) {
            return new ErrorDto(
                    ex.getMessage(),
                    ex.getOperation(),
                    ex.getEntityId()
            );
        }

        @ExceptionHandler(Exception.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)

        public ErrorDto handleOther(Exception ex) {
            return new ErrorDto(
                    ex.getMessage(),
                    null,
                    null
            );
        }
}

