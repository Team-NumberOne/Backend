package com.numberone.backend.auth.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.member.service.MemberService;
import com.numberone.backend.domain.token.util.JwtUtil;
import com.numberone.backend.exception.context.ExceptionContext;
import com.numberone.backend.exception.dto.ErrorResponse;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

import static com.numberone.backend.exception.context.CustomExceptionContext.*;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return !path.startsWith("/api/");
        // /api로 시작하는 경로에 대해서만 jwt 인증을 진행합니다. 이렇게 안하면 security에서 무시한 경로라도 모든 경로에 대해서 이 필터를 거치네요..
        // jwt인증이 필요한 api에 대해서는 /api/apple 처럼 /api로 시작하게 만들어야 될것같아요!
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            setErrorResponse(response, WRONG_ACCESS_TOKEN);
            return;
        }
        String token = authorizationHeader.split(" ")[1];
        if (!jwtUtil.isValid(token)) {
            setErrorResponse(response, WRONG_ACCESS_TOKEN);
            return;
        }
        if (jwtUtil.isExpired(token)) {
            setErrorResponse(response, EXPIRED_ACCESS_TOKEN);
            return;
        }

        String email = jwtUtil.getEmail(token);
        try {
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(NotFoundMemberException::new);;
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    member.getEmail(), null, Collections.emptyList());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        } catch (NotFoundMemberException e) {
            setErrorResponse(response, NOT_FOUND_MEMBER);
            return;
        }
    }

    private void setErrorResponse(
            HttpServletResponse response,
            ExceptionContext context
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");
        ErrorResponse errorResponse = new ErrorResponse(context.getCode(), context.getMessage());
        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
