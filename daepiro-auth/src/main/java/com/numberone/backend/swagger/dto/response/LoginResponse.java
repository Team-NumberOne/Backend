package com.numberone.backend.swagger.dto.response;

import lombok.Getter;

@Getter
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Boolean isOnboarding;
}
