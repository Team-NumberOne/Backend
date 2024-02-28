package com.numberone.backend.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numberone.backend.exception.context.CustomExceptionContext;
import com.numberone.backend.provider.HttpResponseProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import static com.numberone.backend.exception.context.CustomExceptionContext.NOT_AUTHENTICATION;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final HttpResponseProvider httpResponseProvider;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        httpResponseProvider.setErrorResponse(response,HttpServletResponse.SC_UNAUTHORIZED, NOT_AUTHENTICATION);
    }
}
