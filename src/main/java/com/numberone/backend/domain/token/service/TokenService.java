package com.numberone.backend.domain.token.service;

import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.member.service.MemberService;
import com.numberone.backend.domain.token.dto.request.TokenRequest;
import com.numberone.backend.domain.token.dto.response.*;
import com.numberone.backend.domain.token.entity.Token;
import com.numberone.backend.domain.token.repository.TokenRepository;
import com.numberone.backend.properties.KakaoProperties;
import com.numberone.backend.properties.NaverProperties;
import com.numberone.backend.domain.token.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;
    private final KakaoProperties kakaoProperties;
    private final NaverProperties naverProperties;
    private final MemberService memberService;
    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;

    public TokenResponse loginKakao(TokenRequest tokenRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
        headers.add("Authorization", "Bearer " + tokenRequest.getToken());

        ResponseEntity<KakaoInfoResponse> response = restTemplate.exchange(kakaoProperties.getUser_api_url(), HttpMethod.GET, new HttpEntity<>(null, headers), KakaoInfoResponse.class);
        String email = response.getBody().getKakao_account().getEmail();
        return new TokenResponse(getAccessToken(email));
    }

    public TokenResponse loginNaver(TokenRequest tokenRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Bearer " + tokenRequest.getToken());

        ResponseEntity<NaverInfoResponse> response = restTemplate.exchange(naverProperties.getUser_api_url(), HttpMethod.GET, new HttpEntity<>(null, headers), NaverInfoResponse.class);
        String email = response.getBody().getResponse().getEmail();
        return new TokenResponse(getAccessToken(email));
    }

    private String getAccessToken(String email) {
        if (!memberRepository.existsByEmail(email))
            memberService.create(email);
        if (tokenRepository.existsById(email)) {
            Token token = tokenRepository.findById(email)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 토큰"));
            return token.getAccessToken();
        } else {
            String accessToken = jwtUtil.createToken(email, 1000L * 60 * 60 * 24 * 14);//14일
            String refreshToken = jwtUtil.createToken(email, 1000L * 60 * 30);//30분
            tokenRepository.save(Token.builder()
                    .email(email)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build());
            return accessToken;
        }
    }
}
