package com.numberone.backend.exception.badrequest;

import com.numberone.backend.exception.context.ExceptionContext;
import com.numberone.backend.exception.NumberOneException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends NumberOneException {
    public BadRequestException(ExceptionContext context) {
        super(HttpStatus.BAD_REQUEST, context.getMessage(), context.getCode());
    }
}
