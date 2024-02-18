package com.numberone.backend.exception.conflict;

import com.numberone.backend.exception.NumberOneException;
import com.numberone.backend.exception.context.ExceptionContext;
import org.springframework.http.HttpStatus;

public class ConflictException extends NumberOneException {
    public ConflictException(ExceptionContext context) {
        super(HttpStatus.CONFLICT, context.getMessage(), context.getCode());
    }
}
