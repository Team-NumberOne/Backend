package com.numberone.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NumberOneException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;
    private final int code;

    public NumberOneException(HttpStatus httpStatus, String message, int code) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.code = code;
    }

}
