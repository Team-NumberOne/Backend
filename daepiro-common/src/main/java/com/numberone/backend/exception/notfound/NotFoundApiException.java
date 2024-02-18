package com.numberone.backend.exception.notfound;

import static com.numberone.backend.exception.context.CustomExceptionContext.NOT_FOUND_API;

public class NotFoundApiException extends NotFoundException {
    public NotFoundApiException() {
        super(NOT_FOUND_API);
    }
}
