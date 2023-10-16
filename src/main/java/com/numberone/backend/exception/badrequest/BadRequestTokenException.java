package com.numberone.backend.exception.badrequest;

import com.numberone.backend.exception.context.ExceptionContext;

import static com.numberone.backend.exception.context.CustomExceptionContext.BAD_REQUEST_TOKEN;

public class BadRequestTokenException extends BadRequestException{
    public BadRequestTokenException() {
        super(BAD_REQUEST_TOKEN);
    }
}
