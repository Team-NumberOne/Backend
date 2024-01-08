package com.numberone.backend.exception.badrequest;

import static com.numberone.backend.exception.context.CustomExceptionContext.INVALID_INVITE_TYPE;

public class InvalidInviteTypeException extends BadRequestException {

    public InvalidInviteTypeException() {
        super(INVALID_INVITE_TYPE);
    }
}
