package com.shopping.flipkart.exceptionhandler;

import com.shopping.flipkart.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class AuthExceptionHandler {

    private ResponseEntity<Object> exceptionStructure(HttpStatus status, String msg, Object rootCause) {
        return new ResponseEntity<Object>(Map.of("Status", status.value(), "Message", msg, "Root Cause", rootCause),
                status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintValidationException(ConstraintViolationException exception){
        return  exceptionStructure(HttpStatus.valueOf(exception.getStatus()),exception.getMessage(),exception.getRootCause());
    }

}