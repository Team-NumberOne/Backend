package com.numberone.backend.exception.conflict;

import static com.numberone.backend.exception.context.CustomExceptionContext.ALREADY_LIKED_ERROR;

public class AlreadyLikedException extends ConflictException {
    public AlreadyLikedException(){
        super(ALREADY_LIKED_ERROR);
    }
}
