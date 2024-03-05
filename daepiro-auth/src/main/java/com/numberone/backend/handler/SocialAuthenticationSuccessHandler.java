package com.numberone.backend.handler;

import com.numberone.backend.provider.HttpResponseProvider;
import com.numberone.backend.provider.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SocialAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final HttpResponseProvider httpResponseProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        httpResponseProvider.setLoginResponse(response, (long) authentication.getPrincipal());
    }
}
