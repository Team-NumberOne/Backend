package com.numberone.backend.exception.notfound;

import com.numberone.backend.exception.NumberOneException;
import com.numberone.backend.exception.context.ExceptionContext;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends NumberOneException {
    public NotFoundException(ExceptionContext context) {
        super(HttpStatus.NOT_FOUND, context.getMessage(), context.getCode());
    }
}
