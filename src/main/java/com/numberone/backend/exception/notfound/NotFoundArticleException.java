package com.numberone.backend.exception.notfound;

import static com.numberone.backend.exception.context.CustomExceptionContext.NOT_FOUND_ARTICLE;

public class NotFoundArticleException extends NotFoundException {
    public NotFoundArticleException() {
        super(NOT_FOUND_ARTICLE);
    }
}
