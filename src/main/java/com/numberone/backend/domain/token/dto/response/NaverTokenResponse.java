package com.numberone.backend.domain.token.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class NaverTokenResponse {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private String expires_in;
}
