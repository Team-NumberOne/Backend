package com.numberone.backend.exception.notfound;

import static com.numberone.backend.exception.context.CustomExceptionContext.WRONG_REFRESH_TOKEN;

public class NotFoundRefreshTokenException extends NotFoundException {
    public NotFoundRefreshTokenException() {
        super(WRONG_REFRESH_TOKEN);
    }
}
