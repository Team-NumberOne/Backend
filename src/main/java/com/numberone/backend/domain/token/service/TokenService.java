package com.numberone.backend.domain.token.service;

import com.numberone.backend.domain.token.dto.request.TokenRequest;
import com.numberone.backend.domain.token.dto.response.KakaoInfoResponse;
import com.numberone.backend.domain.token.dto.response.KakaoTokenResponse;
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

    public TokenResponse loginKakao(TokenRequest tokenRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> bodys = new LinkedMultiValueMap<>();

        bodys.add("grant_type", "authorization_code");
        bodys.add("client_id", kakaoProperties.getClient_id());
        bodys.add("redirect_uri", kakaoProperties.getRedirect_uri());
        bodys.add("code", tokenRequest.getCode());


        KakaoTokenResponse kakaoResponse = restTemplate.postForObject("https://kauth.kakao.com/oauth/token", new HttpEntity<>(bodys, headers), KakaoTokenResponse.class);

        headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
        headers.add("Authorization", "Bearer " + kakaoResponse.getAccess_token());

        ResponseEntity<KakaoInfoResponse> response = restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.GET, new HttpEntity<>(null, headers), KakaoInfoResponse.class);

        System.out.println(response.getBody().getKakao_account().getEmail());

        String token = jwtUtil.createToken(response.getBody().getKakao_account().getEmail(), 1000L * 60 * 60 * 24 * 14);

        return new TokenResponse(token);
    }

    public TokenResponse loginNaver(TokenRequest tokenRequest){
        return null;
    }
}
