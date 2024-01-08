package com.numberone.backend.domain.token.controller;

import com.numberone.backend.domain.token.dto.request.GetTokenRequest;
import com.numberone.backend.domain.token.dto.request.RefreshTokenRequest;
import com.numberone.backend.domain.token.dto.response.GetTokenResponse;
import com.numberone.backend.domain.token.dto.response.RefreshTokenResponse;
import com.numberone.backend.domain.token.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "token", description = "토큰 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {
    private final TokenService tokenService;

    @Operation(summary = "카카오 토큰을 이용하여 서버 JWT 토큰 발급받기", description =
            """
            카카오 토큰을 body 에 담아서 post 요청 해주세요.
            
            앞으로 서버 요청 시에 사용할 수 있는 JWT 토큰이 발급됩니다.
            
            이후 서버에 API 요청시 이 JWT 토큰을 같이 담아서 요청해야 정상적으로 API가 호출 됩니다.  
            """)
    @PostMapping("/kakao")
    public GetTokenResponse loginKakao(@RequestBody @Valid GetTokenRequest tokenRequest) {
        return tokenService.loginKakao(tokenRequest);
    }

    @Operation(summary = "네이버 토큰을 이용하여 서버 JWT 토큰 발급받기", description =
            """
            네이버 토큰을 body 에 담아서 post 요청 해주세요.
            
            앞으로 서버 요청 시에 사용할 수 있는 JWT 토큰이 발급됩니다.
            
            이후 서버에 API 요청시 이 JWT 토큰을 같이 담아서 요청해야 정상적으로 API가 호출 됩니다.  
            """)
    @PostMapping("/naver")
    public GetTokenResponse loginNaver(@RequestBody @Valid GetTokenRequest tokenRequest) {
        return tokenService.loginNaver(tokenRequest);
    }

    @Operation(summary = "만료된 JWT 토큰 갱신하기", description =
            """
            만료된 JWT 토큰을 body 에 담아서 post 요청 해주세요.
            
            새로 사용할 수 있는 JWT 토큰이 발급됩니다.
            """)
    @PostMapping("/refresh")
    public RefreshTokenResponse refresh(@RequestBody @Valid RefreshTokenRequest tokenRequest) {
        return tokenService.refresh(tokenRequest);
    }
}
