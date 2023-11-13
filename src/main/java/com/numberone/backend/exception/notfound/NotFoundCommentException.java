package com.numberone.backend.exception.notfound;

import static com.numberone.backend.exception.context.CustomExceptionContext.NOT_FOUND_COMMENT;

public class NotFoundCommentException extends NotFoundException {
    public NotFoundCommentException(){
        super(NOT_FOUND_COMMENT);
    }
}
