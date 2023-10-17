package com.numberone.backend.exception.forbidden;

import com.numberone.backend.exception.context.ExceptionContext;

import static com.numberone.backend.exception.context.CustomExceptionContext.EXPIRED_ACCESS_TOKEN;

public class ExpiredAccessTokenException extends ForbiddenException{
    public ExpiredAccessTokenException() {
        super(EXPIRED_ACCESS_TOKEN);
    }
}
