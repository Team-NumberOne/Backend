package com.numberone.backend.exception.notfound;

import com.numberone.backend.exception.context.ExceptionContext;

import static com.numberone.backend.exception.context.CustomExceptionContext.NOT_FOUND_DISASTER;

public class NotFoundDisasterException extends NotFoundException {
    public NotFoundDisasterException() {
        super(NOT_FOUND_DISASTER);
    }
}
