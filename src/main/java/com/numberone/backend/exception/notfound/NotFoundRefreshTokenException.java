package com.numberone.backend.exception.notfound;

import com.numberone.backend.exception.forbidden.ForbiddenException;

import static com.numberone.backend.exception.context.CustomExceptionContext.WRONG_REFRESH_TOKEN;

public class NotFoundRefreshTokenException extends NotFoundException {
    public NotFoundRefreshTokenException() {
        super(WRONG_REFRESH_TOKEN);
    }
}
