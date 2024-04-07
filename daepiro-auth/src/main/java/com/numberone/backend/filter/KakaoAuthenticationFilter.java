package com.numberone.backend.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numberone.backend.handler.SocialAuthenticationFailureHandler;
import com.numberone.backend.handler.SocialAuthenticationSuccessHandler;
import com.numberone.backend.provider.KakaoAuthenticationProvider;
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
public class KakaoAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper;
    private static final String JSON_PARAM = "token";
    private static final String REQUEST_URL = "/token/kakao";
    private static final String REQUEST_METHOD = "POST";


    public KakaoAuthenticationFilter(KakaoAuthenticationProvider kakaoAuthenticationProvider,
                                     SocialAuthenticationSuccessHandler socialAuthenticationSuccessHandler,
                                     SocialAuthenticationFailureHandler socialAuthenticationFailureHandler,
                                     ObjectMapper objectMapper) {
        super(new AntPathRequestMatcher(REQUEST_URL, REQUEST_METHOD));
        setAuthenticationManager(new ProviderManager(kakaoAuthenticationProvider));
        setAuthenticationSuccessHandler(socialAuthenticationSuccessHandler);
        setAuthenticationFailureHandler(socialAuthenticationFailureHandler);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String requestBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        Map<String, String> requestBodyMap = objectMapper.readValue(requestBody, Map.class);
        String token = requestBodyMap.get(JSON_PARAM);
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(token, null);
        return getAuthenticationManager().authenticate(authentication);
    }
}
