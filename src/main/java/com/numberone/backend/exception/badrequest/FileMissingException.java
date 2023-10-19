package com.numberone.backend.exception.badrequest;

import static com.numberone.backend.exception.context.CustomExceptionContext.S3_MULTIPART_MISSING;

public class FileMissingException extends BadRequestException {
    public FileMissingException() {
        super(S3_MULTIPART_MISSING);
    }
}
