package com.numberone.backend.domain.token.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoTokenResponse {
    private String token_type;
    private String access_token;
    private String id_token;
    private String refresh_token;
    private Integer expires_in;
    private Integer refresh_token_expires_in;
    private String scope;
}
