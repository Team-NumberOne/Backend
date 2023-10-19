package com.numberone.backend.domain.token.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefreshTokenResponse {
    private String accessToken;
    private String refreshToken;

    @Builder
    public RefreshTokenResponse(String accessToken,String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken= refreshToken;
    }

    public static RefreshTokenResponse of(String accessToken, String refreshToken) {
        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
