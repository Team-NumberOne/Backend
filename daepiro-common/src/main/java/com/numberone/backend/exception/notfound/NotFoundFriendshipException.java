package com.numberone.backend.exception.notfound;

import static com.numberone.backend.exception.context.CustomExceptionContext.NOT_FOUND_FRIENDSHIP;

public class NotFoundFriendshipException extends NotFoundException {

    public NotFoundFriendshipException() {
        super(NOT_FOUND_FRIENDSHIP);
    }
}
