package com.numberone.backend.exception.notfound;


import com.numberone.backend.exception.context.CustomExceptionContext;
import com.numberone.backend.exception.context.ExceptionContext;

import static com.numberone.backend.exception.context.CustomExceptionContext.NOT_FOUND_ARTICLE_LIKE;

public class NotFoundArticleLikeException extends NotFoundException {
    public NotFoundArticleLikeException() {
        super(NOT_FOUND_ARTICLE_LIKE);
    }
}
