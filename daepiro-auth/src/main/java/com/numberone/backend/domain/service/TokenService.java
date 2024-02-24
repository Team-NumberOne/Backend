package com.numberone.backend.domain.service;

import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.properties.KakaoProperties;
import com.numberone.backend.domain.properties.NaverProperties;
import com.numberone.backend.domain.properties.TokenPeriodProperties;
import com.numberone.backend.domain.token.dto.request.GetTokenRequest;
import com.numberone.backend.domain.token.dto.request.RefreshTokenRequest;
import com.numberone.backend.domain.token.dto.response.GetTokenResponse;
import com.numberone.backend.domain.token.dto.response.KakaoInfoResponse;
import com.numberone.backend.domain.token.dto.response.NaverInfoResponse;
import com.numberone.backend.domain.token.dto.response.RefreshTokenResponse;
import com.numberone.backend.domain.token.entity.Token;
import com.numberone.backend.domain.token.repository.TokenRepository;
import com.numberone.backend.exception.badrequest.BadRequestSocialTokenException;
import com.numberone.backend.exception.forbidden.WrongAccessTokenException;
import com.numberone.backend.exception.notfound.NotFoundRefreshTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TokenService {

    private final JwtProvider jwtProvider;

    private final RestTemplate restTemplate;

    private final KakaoProperties kakaoProperties;
    private final NaverProperties naverProperties;
    private final TokenPeriodProperties tokenPeriodProperties;

    private final MemberRepository repository;
    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public GetTokenResponse loginKakao(GetTokenRequest tokenRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
        headers.add("Authorization", "Bearer " + tokenRequest.getToken());

        try {
            ResponseEntity<KakaoInfoResponse> response = restTemplate.exchange(kakaoProperties.getUser_api_url(), HttpMethod.GET, new HttpEntity<>(null, headers), KakaoInfoResponse.class);
            String email = response.getBody().getKakao_account().getEmail();
//            String realName = "실명을 가져올 수 없습니다.";
//            if(response.getBody().getKakao_account().getProfile() != null){
//                realName = response.getBody().getKakao_account().getProfile().getNickname();
//            }
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
//            String realName = Optional.ofNullable(response.getBody().getResponse().getName())
//                    .orElse("실명을 가져올 수 없습니다.");
            return getTokenResponse(email);
        } catch (Exception e) {
            throw new BadRequestSocialTokenException();
        }
    }

    @Transactional
    public RefreshTokenResponse refresh(RefreshTokenRequest tokenRequest) {
        if (!jwtProvider.isValid(tokenRequest.getToken()))
            throw new NotFoundRefreshTokenException();
        Token token = tokenRepository.findByRefreshToken(tokenRequest.getToken())
                .orElseThrow(NotFoundRefreshTokenException::new);
        String email = jwtProvider.getEmail(tokenRequest.getToken());
        String newAccessToken = jwtProvider.createToken(email, tokenPeriodProperties.getAccess());
        String newRefreshToken = jwtProvider.createToken(email, tokenPeriodProperties.getRefresh());
        token.update(newAccessToken, newRefreshToken);
        tokenRepository.save(token);//redis의 경우 jpa와 달리 transactional을 이용해도 데이터 수정시에 명시적으로 save를 해줘야 함
        return RefreshTokenResponse.of(newAccessToken, newRefreshToken);
    }

    private GetTokenResponse getTokenResponse(String email) {
        Boolean isNewMember = false;
        if (!memberRepository.existsByEmail(email)) {
            memberRepository.save(Member.of(email));
            isNewMember = true;
        }
        if (tokenRepository.existsById(email)) {
            Token token = tokenRepository.findById(email)
                    .orElseThrow(WrongAccessTokenException::new);
            return GetTokenResponse.of(token.getAccessToken(), token.getRefreshToken(), isNewMember);
        } else {
            String refreshToken = jwtProvider.createToken(email, tokenPeriodProperties.getRefresh());
            String accessToken = jwtProvider.createToken(email, tokenPeriodProperties.getAccess());
            Token token = tokenRepository.save(Token.of(email, accessToken, refreshToken));
            return GetTokenResponse.of(token.getAccessToken(), token.getRefreshToken(), isNewMember);
        }
    }
}