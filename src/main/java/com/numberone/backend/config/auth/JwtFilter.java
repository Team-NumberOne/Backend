package com.numberone.backend.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.service.MemberService;
import com.numberone.backend.domain.token.dto.response.ErrorResponse;
import com.numberone.backend.domain.token.util.JwtUtil;
import com.numberone.backend.exception.context.ExceptionContext;
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

import static com.numberone.backend.exception.context.CustomExceptionContext.WRONG_ACCESS_TOKEN;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final MemberService memberService;

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
            //여기서는 클라이언트에 응답시에 토큰이 잘못되었다는 exception을 날리고싶다.
            setErrorResponse(response,WRONG_ACCESS_TOKEN);
            return;
        }

        String token = authorizationHeader.split(" ")[1];
        if (jwtUtil.isExpired(token)) {
            //여기서는 클라이언트에 응답시에 만료된 토큰이라는 exception을 날리고싶다. 만료 체크와 jwt형식 유효성 체크 구분이 힘드네요. 한번에 처리했습니다
            setErrorResponse(response,WRONG_ACCESS_TOKEN);
            return;
        }

        String email = jwtUtil.getEmail(token);
        if (email == null)//jwt토큰 자체가 형식에 안맞아서 이메일 추출에 실패했을 경우
        {
            setErrorResponse(response,WRONG_ACCESS_TOKEN);
            return;
        }
        Member member = memberService.findByEmail(email);
        //토큰이 jwt토큰이라 이메일 추출은 됬지만 해당 이메일을 가진 멤버가 없을경우 WrongAccessToken예외가 아니라 NotFoundMember예외를 발생시킴

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                member.getEmail(), null, Collections.emptyList());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
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
