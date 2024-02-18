package com.numberone.backend.domain.token.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetTokenResponse {
    private String accessToken;
    private String refreshToken;
    private Boolean isNewMember;

    @Builder
    public GetTokenResponse(String accessToken, String refreshToken, Boolean isNewMember) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isNewMember = isNewMember;
    }

    public static GetTokenResponse of(String accessToken, String refreshToken, Boolean isNewMember) {
        return GetTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isNewMember(isNewMember)
                .build();
    }
}
