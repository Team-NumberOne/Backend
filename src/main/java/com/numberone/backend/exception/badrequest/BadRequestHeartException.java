package com.numberone.backend.exception.badrequest;

import com.numberone.backend.exception.context.ExceptionContext;

import static com.numberone.backend.exception.context.CustomExceptionContext.BAD_REQUEST_HEART;

public class BadRequestHeartException extends BadRequestException{

    public BadRequestHeartException() {
        super(BAD_REQUEST_HEART);
    }
}
