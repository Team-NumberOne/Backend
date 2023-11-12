package com.numberone.backend.exception.notfound;

import static com.numberone.backend.exception.context.CustomExceptionContext.NOT_FOUND_ARTICLE_IMAGE;

public class NotFoundArticleImageException extends NotFoundException {
    public NotFoundArticleImageException() {
        super(NOT_FOUND_ARTICLE_IMAGE);
    }
}
