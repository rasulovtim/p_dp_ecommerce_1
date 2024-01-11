package com.gitlab.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ErrorResponseDto handleAclCabinetServiceException(EntityNotFoundException ex) {
        return new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}