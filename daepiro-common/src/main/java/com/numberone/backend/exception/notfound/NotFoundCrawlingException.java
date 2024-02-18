package com.numberone.backend.exception.notfound;

import static com.numberone.backend.exception.context.CustomExceptionContext.NOT_FOUND_CRAWLING;

public class NotFoundCrawlingException extends NotFoundException {
    public NotFoundCrawlingException() {
        super(NOT_FOUND_CRAWLING);
    }
}
