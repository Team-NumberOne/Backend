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

    private static final String PARAM_ACCESS = "accessToken";
    private static final String PARAM_REFRESH = "refreshToken";
    private static final String PARAM_ISONBOARDING = "isOnboarding";
    private static final String PARAM_CODE = "code";
    private static final String PARAM_MESSAGE = "message";
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    public void setJwtResponse(HttpServletResponse response, long id) throws IOException {
        String accessToken = jwtProvider.createAccessToken(id);
        String refreshToken = jwtProvider.createRefreshToken(id);
        Map<String, Object> responseBody = new HashMap<>();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(CONTENT_TYPE);
        responseBody.put(PARAM_ACCESS, accessToken);
        responseBody.put(PARAM_REFRESH, refreshToken);
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
        response.setContentType(CONTENT_TYPE);
        responseBody.put(PARAM_ACCESS, accessToken);
        responseBody.put(PARAM_REFRESH, refreshToken);
        responseBody.put(PARAM_ISONBOARDING, isOnboarding);
        objectMapper.writeValue(response.getWriter(), responseBody);
    }

    public void setErrorResponse(HttpServletResponse response, int statusCode, CustomExceptionContext exception) throws IOException {
        Map<String, Object> responseBody = new HashMap<>();
        response.setStatus(statusCode);
        response.setContentType(CONTENT_TYPE);
        responseBody.put(PARAM_CODE, exception.getCode());
        responseBody.put(PARAM_MESSAGE, exception.getMessage());
        objectMapper.writeValue(response.getWriter(), responseBody);
    }
}
