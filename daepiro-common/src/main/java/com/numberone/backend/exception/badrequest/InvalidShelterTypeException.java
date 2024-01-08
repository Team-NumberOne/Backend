package com.numberone.backend.exception.badrequest;

import static com.numberone.backend.exception.context.CustomExceptionContext.INVALID_SHELTER_TYPE;

public class InvalidShelterTypeException extends BadRequestException {
    public InvalidShelterTypeException() {
        super(INVALID_SHELTER_TYPE);
    }
}
