package com.numberone.backend.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.exception.context.CustomExceptionContext;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HttpResponseProvider {
    private final ObjectMapper objectMapper;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    public void setJwtResponse(HttpServletResponse response, long id) throws IOException {
        String accessToken = jwtProvider.createAccessToken(id);
        String refreshToken = jwtProvider.createRefreshToken(id);
        Map<String, Object> responseBody = new HashMap<>();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        responseBody.put("accessToken", accessToken);
        responseBody.put("refreshToken", refreshToken);
        objectMapper.writeValue(response.getWriter(), responseBody);
    }

    public void setLoginResponse(HttpServletResponse response, long id) throws IOException {
        String accessToken = jwtProvider.createAccessToken(id);
        String refreshToken = jwtProvider.createRefreshToken(id);
        boolean isOnboarding = memberRepository.findById(id)
                .orElseThrow(NotFoundMemberException::new)
                .getIsOnboarding();
        Map<String, Object> responseBody = new HashMap<>();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        responseBody.put("accessToken", accessToken);
        responseBody.put("refreshToken", refreshToken);
        responseBody.put("isOnboarding", isOnboarding);
        objectMapper.writeValue(response.getWriter(), responseBody);
    }

    public void setErrorResponse(HttpServletResponse response, int statusCode, CustomExceptionContext exception) throws IOException {
        Map<String, Object> responseBody = new HashMap<>();
        response.setStatus(statusCode);
        response.setContentType("application/json;charset=UTF-8");
        responseBody.put("code", exception.getCode());
        responseBody.put("message", exception.getMessage());
        objectMapper.writeValue(response.getWriter(), responseBody);
    }
}
