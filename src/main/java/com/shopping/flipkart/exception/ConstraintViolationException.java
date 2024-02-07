package com.shopping.flipkart.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ConstraintViolationException extends RuntimeException{

    private String message;
    private int status;
    private String rootCause;

}
