package com.numberone.backend.exception.forbidden;

import static com.numberone.backend.exception.context.CustomExceptionContext.WRONG_REFRESH_TOKEN;

public class WrongRefreshTokenException extends ForbiddenException {
    public WrongRefreshTokenException() {
        super(WRONG_REFRESH_TOKEN);
    }
}
