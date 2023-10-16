package com.numberone.backend.domain.token.util;

import com.numberone.backend.domain.token.service.TokenService;
import com.numberone.backend.properties.JwtProperties;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtProperties jwtProperties;

    public String createToken(String email, long period) {
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + period))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret().getBytes())
                .compact();
    }

    public String getEmail(String token) {
        return getClaims(token).getSubject();//claim에서 subject를 제대로 추출하지 못하면 내부적으로 null값을 리턴한다고 함.
    }

    public boolean isExpired(String token) {
        try {
            return getClaims(token).getExpiration().before(new Date());
        } catch (JwtException e) {
            // 하드코딩.......
            // 만료된 토큰 또는 유효하지 않은 토큰에 대한 예외 처리
            return true; // 해당 토큰이 유효하지 않다고 가정하고 만료된 것으로 처리
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecret().getBytes()).parseClaimsJws(token).getBody();
    }
}
