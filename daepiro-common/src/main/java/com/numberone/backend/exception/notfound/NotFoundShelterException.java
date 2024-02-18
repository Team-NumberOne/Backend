package com.numberone.backend.exception.notfound;

import static com.numberone.backend.exception.context.CustomExceptionContext.NOT_FOUND_SHELTER;

public class NotFoundShelterException extends NotFoundException {
    public NotFoundShelterException() {
        super(NOT_FOUND_SHELTER);
    }
}
