package com.numberone.backend.exception.forbidden;

import com.numberone.backend.exception.NumberOneException;
import com.numberone.backend.exception.context.ExceptionContext;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends NumberOneException {
    public ForbiddenException(ExceptionContext context) {
        super(HttpStatus.FORBIDDEN,context.getMessage(),context.getCode());
    }
}
