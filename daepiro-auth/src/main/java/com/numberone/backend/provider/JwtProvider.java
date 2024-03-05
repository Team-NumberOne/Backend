package com.numberone.backend.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.token.service.RefreshTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${spring.jwt.secret}")
    private String secretKey;
    private final RefreshTokenService refreshTokenService;
    private final MemberRepository memberRepository;

    public String createAccessToken(long id) {
        long accessTokenPeroid = 1000L * 60 * 30;
        return Jwts.builder()
                .claim("id", id)
                .claim("type", "access")
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + accessTokenPeroid))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public String createRefreshToken(long id) {
        long refreshTokenPeroid = 1000L * 60 * 60 * 24 * 14;
        String token = Jwts.builder()
                .claim("id", id)
                .claim("type", "refresh")
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + refreshTokenPeroid))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
        refreshTokenService.update(id, token);
        return token;
    }

    public Long checkToken(String jwt, String tokenType, HttpServletRequest request) {
        if (tokenType.equals("access")) {//나중에는 refresh token도 이렇게 처리하도록 수정할 것임
            if (!jwt.startsWith("Bearer "))
                throw new JwtException("유효하지 않은 토큰입니다.");
            jwt = jwt.substring("Bearer ".length());
        }
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
        long id = ((Number) claims.get("id")).longValue();
        String type = (String) claims.get("type");
        if (!memberRepository.existsById(id) || !type.equals(tokenType))
            throw new JwtException("유효하지 않은 토큰입니다.");
        return id;
    }
}
