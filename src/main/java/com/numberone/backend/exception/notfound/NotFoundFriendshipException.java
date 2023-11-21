package com.numberone.backend.exception.notfound;

import com.numberone.backend.exception.context.ExceptionContext;

import static com.numberone.backend.exception.context.CustomExceptionContext.NOT_FOUND_FRIENDSHIP;

public class NotFoundFriendshipException extends NotFoundException {

    public NotFoundFriendshipException() {
        super(NOT_FOUND_FRIENDSHIP);
    }
}
