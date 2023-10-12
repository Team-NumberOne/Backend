package com.numberone.backend.domain.token.service;

import com.numberone.backend.domain.token.dto.request.TokenRequest;
import com.numberone.backend.domain.token.dto.response.KakaoResponse;
import com.numberone.backend.domain.token.dto.response.TokenResponse;
import com.numberone.backend.properties.KakaoProperties;
import com.numberone.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;
    private final KakaoProperties kakaoProperties;

    public TokenResponse login(TokenRequest tokenRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> bodys = new LinkedMultiValueMap<>();

        bodys.add("grant_type", "authorization_code");
        bodys.add("client_id", kakaoProperties.getClient_id());
        bodys.add("redirect_uri", kakaoProperties.getRedirect_uri());
        bodys.add("code", tokenRequest.getCode());

        System.out.println(bodys);

        KakaoResponse kakaoResponse = restTemplate.postForObject("https://kauth.kakao.com/oauth/token", new HttpEntity<>(bodys, headers), KakaoResponse.class);

        System.out.println(kakaoResponse.getAccess_token());

        headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
        headers.add("Authorization", "Bearer " + kakaoResponse.getAccess_token());

        ResponseEntity<String> response = restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.GET, new HttpEntity<>(null, headers), String.class);

        String body = response.getBody();
        System.out.println(body);

        String token = jwtUtil.createToken("shane9747@naver.com", 1000L * 60 * 60 * 24 * 14);

        return new TokenResponse(token);
    }
}
