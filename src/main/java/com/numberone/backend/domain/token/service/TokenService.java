package com.numberone.backend.domain.token.service;

import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.member.service.MemberService;
import com.numberone.backend.domain.token.dto.request.TokenRequest;
import com.numberone.backend.domain.token.dto.response.*;
import com.numberone.backend.properties.KakaoProperties;
import com.numberone.backend.properties.NaverProperties;
import com.numberone.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
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
    private final NaverProperties naverProperties;
    private final MemberService memberService;

    public TokenResponse loginKakao(TokenRequest tokenRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
        headers.add("Authorization", "Bearer " + tokenRequest.getCode());

        ResponseEntity<KakaoInfoResponse> response = restTemplate.exchange(kakaoProperties.getUser_api_url(), HttpMethod.GET, new HttpEntity<>(null, headers), KakaoInfoResponse.class);
        String token = jwtUtil.createToken(response.getBody().getKakao_account().getEmail(), 1000L * 60 * 60 * 24 * 14);
        memberService.login(response.getBody().getKakao_account().getEmail());
        return new TokenResponse(token);
    }

    public TokenResponse loginNaver(TokenRequest tokenRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Bearer " + tokenRequest.getCode());

        ResponseEntity<NaverInfoResponse> response = restTemplate.exchange(naverProperties.getUser_api_url(), HttpMethod.GET, new HttpEntity<>(null, headers), NaverInfoResponse.class);
        String token = jwtUtil.createToken(response.getBody().getResponse().getEmail(), 1000L * 60 * 60 * 24 * 14);
        memberService.login(response.getBody().getResponse().getEmail());
        return new TokenResponse(token);
    }
}
