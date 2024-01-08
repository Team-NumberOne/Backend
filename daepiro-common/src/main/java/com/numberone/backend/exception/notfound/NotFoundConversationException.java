package com.numberone.backend.exception.notfound;

import static com.numberone.backend.exception.context.CustomExceptionContext.NOT_FOUND_CONVERSATION;

public class NotFoundConversationException extends NotFoundException {

    public NotFoundConversationException() {
        super(NOT_FOUND_CONVERSATION);
    }
}
