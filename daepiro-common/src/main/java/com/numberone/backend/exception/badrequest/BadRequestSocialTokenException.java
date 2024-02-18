package com.numberone.backend.exception.badrequest;

import static com.numberone.backend.exception.context.CustomExceptionContext.BAD_REQUEST_SOCIAL_TOKEN;

public class BadRequestSocialTokenException extends BadRequestException{
    public BadRequestSocialTokenException() {
        super(BAD_REQUEST_SOCIAL_TOKEN);
    }
}
