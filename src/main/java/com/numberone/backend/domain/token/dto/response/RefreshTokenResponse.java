package com.numberone.backend.domain.token.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefreshTokenResponse {
    private String accessToken;

    @Builder
    public RefreshTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public static RefreshTokenResponse of(String accessToken) {
        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
