package com.numberone.backend.domain.token.util;

import com.numberone.backend.properties.JwtProperties;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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


    public boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isExpired(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtProperties.getSecret().getBytes()).parseClaimsJws(token);
            return getClaims(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecret().getBytes()).parseClaimsJws(token).getBody();
    }
}
