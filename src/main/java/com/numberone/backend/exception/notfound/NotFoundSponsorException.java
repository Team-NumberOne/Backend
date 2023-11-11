package com.numberone.backend.exception.notfound;


import static com.numberone.backend.exception.context.CustomExceptionContext.NOT_FOUND_SPONSOR;

public class NotFoundSponsorException extends NotFoundException{
    public NotFoundSponsorException() {
        super(NOT_FOUND_SPONSOR);
    }
}
