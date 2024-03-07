package com.numberone.backend.provider;

import com.numberone.backend.TokenType;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.token.service.RefreshTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${spring.jwt.secret}")
    private String secretKey;
    @Value("${spring.jwt.access}")
    private Long accessTokenPeriod;
    @Value("${spring.jwt.refresh}")
    private Long refreshTokenPeriod;
    private final RefreshTokenService refreshTokenService;
    private final MemberRepository memberRepository;

    private static final String CLAIM_ID = "id";
    private static final String CLAIM_TYPE = "type";
    public static final String PREFIX_BEARER = "Bearer ";

    public String createAccessToken(long id) {
        return Jwts.builder()
                .claim(CLAIM_ID, id)
                .claim(CLAIM_TYPE, TokenType.ACCESS)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + accessTokenPeriod))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public String createRefreshToken(long id) {
        String token = Jwts.builder()
                .claim(CLAIM_ID, id)
                .claim(CLAIM_TYPE, TokenType.REFRESH)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + refreshTokenPeriod))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
        refreshTokenService.update(id, token);
        return token;
    }

    public Long checkToken(String jwt, TokenType tokenType, HttpServletRequest request) {
        if (TokenType.ACCESS.equals(tokenType)) {//나중에는 refresh token도 이렇게 처리하도록 수정할 것임
            if (!jwt.startsWith(PREFIX_BEARER))
                throw new JwtException("유효하지 않은 토큰입니다.");
            jwt = jwt.substring(PREFIX_BEARER.length());
        }
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
        long id = ((Number) claims.get("id")).longValue();
        String type = (String) claims.get(CLAIM_TYPE);
        if (!memberRepository.existsById(id) || !type.equals(tokenType.getDescription()))
            throw new JwtException("유효하지 않은 토큰입니다.");
        return id;
    }
}
