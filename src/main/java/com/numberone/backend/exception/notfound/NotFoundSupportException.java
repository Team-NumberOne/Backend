package com.numberone.backend.exception.notfound;

import static com.numberone.backend.exception.context.CustomExceptionContext.NOT_FOUND_SUPPORT;

public class NotFoundSupportException extends NotFoundException{
    public NotFoundSupportException() {
        super(NOT_FOUND_SUPPORT);
    }
}
