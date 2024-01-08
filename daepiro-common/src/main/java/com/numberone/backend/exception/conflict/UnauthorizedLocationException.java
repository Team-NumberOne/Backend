package com.numberone.backend.exception.conflict;

import static com.numberone.backend.exception.context.CustomExceptionContext.UNAUTHORIZED_LOCATION_ERROR;

public class UnauthorizedLocationException extends ConflictException {
    public UnauthorizedLocationException() {
        super(UNAUTHORIZED_LOCATION_ERROR);
    }
}
