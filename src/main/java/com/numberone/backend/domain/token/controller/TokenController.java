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
@RequestMapping("/login")
public class TokenController {
    private final TokenService tokenService;

    @PostMapping()
    public TokenResponse login(@RequestBody TokenRequest tokenRequest){
        return tokenService.login(tokenRequest);
    }
}
