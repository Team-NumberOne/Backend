package com.numberone.backend.swagger.dto.response;

import lombok.Getter;

@Getter
public class JwtRefreshResponse {
    private String accessToken;
    private String refreshToken;
}
