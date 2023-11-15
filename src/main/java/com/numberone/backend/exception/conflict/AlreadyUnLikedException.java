package com.numberone.backend.exception.conflict;

import static com.numberone.backend.exception.context.CustomExceptionContext.ALREADY_UNLIKED_ERROR;

public class AlreadyUnLikedException extends ConflictException {
    public AlreadyUnLikedException() {
        super(ALREADY_UNLIKED_ERROR);
    }
}
