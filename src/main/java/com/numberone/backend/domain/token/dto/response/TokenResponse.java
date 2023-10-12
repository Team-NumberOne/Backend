package com.numberone.backend.domain.token.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenResponse {
    private String token;

    @Builder
    public TokenResponse(String token) {
        this.token = token;
    }
}
