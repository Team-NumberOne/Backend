package com.numberone.backend.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numberone.backend.handler.SocialAuthenticationFailureHandler;
import com.numberone.backend.handler.SocialAuthenticationSuccessHandler;
import com.numberone.backend.provider.SocialAuthenticationProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class SocialAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper;

    public SocialAuthenticationFilter(SocialAuthenticationProvider socialAuthenticationProvider,
                                      SocialAuthenticationSuccessHandler socialAuthenticationSuccessHandler,
                                      SocialAuthenticationFailureHandler socialAuthenticationFailureHandler,
                                      ObjectMapper objectMapper) {
        super(new AntPathRequestMatcher("/token/kakao", "POST"));
        setAuthenticationManager(new ProviderManager(socialAuthenticationProvider));
        setAuthenticationSuccessHandler(socialAuthenticationSuccessHandler);
        setAuthenticationFailureHandler(socialAuthenticationFailureHandler);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String requestBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        Map<String, String> requestBodyMap = objectMapper.readValue(requestBody, Map.class);
        String token = requestBodyMap.get("token");
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(token, null);
        return getAuthenticationManager().authenticate(authentication);
    }
}
