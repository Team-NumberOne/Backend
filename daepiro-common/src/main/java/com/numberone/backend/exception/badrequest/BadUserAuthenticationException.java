package com.numberone.backend.exception.badrequest;

import static com.numberone.backend.exception.context.CustomExceptionContext.BAD_USER_AUTHENTICATION;

public class BadUserAuthenticationException extends BadRequestException {
    public BadUserAuthenticationException(){
        super(BAD_USER_AUTHENTICATION);
    }
}
