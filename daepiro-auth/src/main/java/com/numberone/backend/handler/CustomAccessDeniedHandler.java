package com.numberone.backend.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.numberone.backend.provider.HttpResponseProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.numberone.backend.exception.context.CustomExceptionContext.NOT_AUTHENTICATION;
import static com.numberone.backend.exception.context.CustomExceptionContext.NOT_AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final HttpResponseProvider httpResponseProvider;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        httpResponseProvider.setErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, NOT_AUTHORIZATION);
    }
}
