package com.numberone.backend.exception.forbidden;

import static com.numberone.backend.exception.context.CustomExceptionContext.WRONG_ACCESS_TOKEN;

public class WrongAccessTokenException extends ForbiddenException {
    public WrongAccessTokenException() {
        super(WRONG_ACCESS_TOKEN);
    }
}
