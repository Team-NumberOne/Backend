package com.numberone.backend.exception.notfound;

import static com.numberone.backend.exception.context.CustomExceptionContext.NOT_FOUND_MEMBER;

public class NotFoundMemberException extends NotFoundException {
    public NotFoundMemberException() {
        super(NOT_FOUND_MEMBER);
    }
}
