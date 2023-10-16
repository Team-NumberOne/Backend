package com.numberone.backend.domain.token.dto.request;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenRequest {
    private String token;
}
