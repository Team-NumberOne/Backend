package com.numberone.backend.handler;

import com.numberone.backend.provider.HttpResponseProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.numberone.backend.exception.context.CustomExceptionContext.LOGIN_FAILURE;
import static com.numberone.backend.exception.context.CustomExceptionContext.NOT_AUTHENTICATION;

@Component
@RequiredArgsConstructor
public class SocialAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final HttpResponseProvider httpResponseProvider;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        httpResponseProvider.setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, LOGIN_FAILURE);
    }
}
