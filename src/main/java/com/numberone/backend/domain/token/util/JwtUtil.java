package com.numberone.backend.domain.token.util;

import com.numberone.backend.domain.token.service.TokenService;
import com.numberone.backend.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
        return getClaims(token).getSubject();
    }

    public boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecret().getBytes()).parseClaimsJws(token).getBody();
    }
}
