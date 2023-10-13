package com.numberone.backend.domain.token.controller;

import com.numberone.backend.domain.token.dto.request.TokenRequest;
import com.numberone.backend.domain.token.dto.response.TokenResponse;
import com.numberone.backend.domain.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/kakao")
    public TokenResponse loginKakao(@RequestBody TokenRequest tokenRequest) {
        return tokenService.loginKakao(tokenRequest);
    }

    @PostMapping("/naver")
    public TokenResponse loginNaver(@RequestBody TokenRequest tokenRequest) {
        return tokenService.loginNaver(tokenRequest);
    }
}
