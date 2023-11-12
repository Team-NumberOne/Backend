package com.numberone.backend.exception.conflict;

import static com.numberone.backend.exception.context.CustomExceptionContext.FIREBASE_INITIALIZATION_FAILED;

public class FirebaseInitializationException extends ConflictException {
    public FirebaseInitializationException() {
        super(FIREBASE_INITIALIZATION_FAILED);
    }
}
