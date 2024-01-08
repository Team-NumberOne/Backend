package com.numberone.backend.exception.badrequest;

import static com.numberone.backend.exception.context.CustomExceptionContext.BAD_REQUEST_CONVERSATION_SORT;

public class BadRequestConversationSortException extends BadRequestException{
    public BadRequestConversationSortException() {
        super(BAD_REQUEST_CONVERSATION_SORT);
    }
}
