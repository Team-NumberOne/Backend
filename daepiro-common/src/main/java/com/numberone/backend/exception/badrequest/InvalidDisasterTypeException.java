package com.numberone.backend.exception.badrequest;

import static com.numberone.backend.exception.context.CustomExceptionContext.INVALID_DISASTER_TYPE;

public class InvalidDisasterTypeException extends BadRequestException {
    public InvalidDisasterTypeException() {
        super(INVALID_DISASTER_TYPE);
    }
}
