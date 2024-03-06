package com.numberone.backend.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numberone.backend.TokenType;
import com.numberone.backend.domain.token.entity.RefreshToken;
import com.numberone.backend.domain.token.service.RefreshTokenService;
import com.numberone.backend.provider.HttpResponseProvider;
import com.numberone.backend.provider.JwtProvider;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtRefreshFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final HttpResponseProvider httpResponseProvider;
    private final RefreshTokenService refreshTokenService;

    private static final String JSON_PARAM = "token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        Map<String, String> requestBodyMap = objectMapper.readValue(requestBody, Map.class);
        String jwt = requestBodyMap.get(JSON_PARAM);
        if (jwt != null) {
            RefreshToken refreshToken = refreshTokenService.findByToken(jwt)
                    .orElseThrow(() -> new JwtException("유효하지 않은 토큰입니다."));
            long id = jwtProvider.checkToken(refreshToken.getToken(), TokenType.REFRESH, request);
            httpResponseProvider.setJwtResponse(response, id);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !"/token/refresh".equals(request.getServletPath());
    }
}
