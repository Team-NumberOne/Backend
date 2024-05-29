package com.numberone.backend.exception.notfound;

import com.numberone.backend.exception.context.ExceptionContext;

import static com.numberone.backend.exception.context.CustomExceptionContext.NOT_FOUND_COMMENT_LIKE;

public class NotFoundCommentLikeException extends NotFoundException{
    public NotFoundCommentLikeException() {
        super(NOT_FOUND_COMMENT_LIKE);
    }
}
