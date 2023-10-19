package com.numberone.backend.exception.badrequest;

import static com.numberone.backend.exception.context.CustomExceptionContext.S3_FILE_UPLOAD_FAILED;

public class FileUploadException extends BadRequestException {
    public FileUploadException() {
        super(S3_FILE_UPLOAD_FAILED);
    }
}
