package com.numberone.backend.domain.token.service;

import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.member.service.MemberService;
import com.numberone.backend.domain.token.dto.request.GetTokenRequest;
import com.numberone.backend.domain.token.dto.request.RefreshTokenRequest;
import com.numberone.backend.domain.token.dto.response.*;
import com.numberone.backend.domain.token.entity.Token;
import com.numberone.backend.domain.token.repository.TokenRepository;
import com.numberone.backend.exception.badrequest.BadRequestSocialTokenException;
import com.numberone.backend.exception.forbidden.WrongAccessTokenException;
import com.numberone.backend.exception.notfound.NotFoundRefreshTokenException;
import com.numberone.backend.properties.KakaoProperties;
import com.numberone.backend.properties.NaverProperties;
import com.numberone.backend.domain.token.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;
    private final KakaoProperties kakaoProperties;
    private final NaverProperties naverProperties;
    private final MemberService memberService;
    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;
    private long refreshPeroid = 1000L * 60 * 60 * 24 * 14;//14일
    private long accessPeroid = 1000L * 60 * 30;//30분

    @Transactional
    public GetTokenResponse loginKakao(GetTokenRequest tokenRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
        headers.add("Authorization", "Bearer " + tokenRequest.getToken());

        try {
            ResponseEntity<KakaoInfoResponse> response = restTemplate.exchange(kakaoProperties.getUser_api_url(), HttpMethod.GET, new HttpEntity<>(null, headers), KakaoInfoResponse.class);
            String email = response.getBody().getKakao_account().getEmail();
            return getTokenResponse(email);
        } catch (Exception e) {
            throw new BadRequestSocialTokenException();
        }
    }

    @Transactional
    public GetTokenResponse loginNaver(GetTokenRequest tokenRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Bearer " + tokenRequest.getToken());

        try {
            ResponseEntity<NaverInfoResponse> response = restTemplate.exchange(naverProperties.getUser_api_url(), HttpMethod.GET, new HttpEntity<>(null, headers), NaverInfoResponse.class);
            String email = response.getBody().getResponse().getEmail();
            return getTokenResponse(email);
        } catch (Exception e) {
            throw new BadRequestSocialTokenException();
        }
    }

    @Transactional
    public RefreshTokenResponse refresh(RefreshTokenRequest tokenRequest) {
        if(!jwtUtil.isValid(tokenRequest.getToken()))
            throw new NotFoundRefreshTokenException();
        Token token = tokenRepository.findByRefreshToken(tokenRequest.getToken())
                .orElseThrow(NotFoundRefreshTokenException::new);
        String email = jwtUtil.getEmail(tokenRequest.getToken());
        String newAccessToken = jwtUtil.createToken(email, accessPeroid);
        String newRefreshToken = jwtUtil.createToken(email, refreshPeroid);
        token.update(newAccessToken,newRefreshToken);
        tokenRepository.save(token);//redis의 경우 jpa와 달리 transactional을 이용해도 데이터 수정시에 명시적으로 save를 해줘야 함
        return RefreshTokenResponse.of(newAccessToken,newRefreshToken);
    }

    @Transactional
    private GetTokenResponse getTokenResponse(String email) {
        if (!memberRepository.existsByEmail(email))
            memberService.create(email);
        if (tokenRepository.existsById(email)) {
            Token token = tokenRepository.findById(email)
                    .orElseThrow(WrongAccessTokenException::new);
            return GetTokenResponse.of(token.getAccessToken(), token.getRefreshToken());
        } else {
            String refreshToken = jwtUtil.createToken(email, refreshPeroid);
            String accessToken = jwtUtil.createToken(email, accessPeroid);
            Token token = tokenRepository.save(Token.of(email, accessToken, refreshToken));
            return GetTokenResponse.of(token.getAccessToken(), token.getRefreshToken());
        }
    }
}
