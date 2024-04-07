package com.numberone.backend.filter;

import com.numberone.backend.exception.context.CustomExceptionContext;
import com.numberone.backend.provider.HttpResponseProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.numberone.backend.exception.context.CustomExceptionContext.EXPIRED_TOKEN;
import static com.numberone.backend.exception.context.CustomExceptionContext.INVALID_TOKEN;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final HttpResponseProvider httpResponseProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            httpResponseProvider.setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, EXPIRED_TOKEN);
        } catch (JwtException e) {
            httpResponseProvider.setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, INVALID_TOKEN);
        }
    }
}
