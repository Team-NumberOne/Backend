package com.numberone.backend.exception.conflict;

import static com.numberone.backend.exception.context.CustomExceptionContext.FIREBASE_MESSAGE_SEND_ERROR;

public class FirebaseMessageSendException extends ConflictException {
    public FirebaseMessageSendException() {
        super(FIREBASE_MESSAGE_SEND_ERROR);
    }
}
