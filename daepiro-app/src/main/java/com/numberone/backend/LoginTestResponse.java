package com.numberone.backend;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginTestResponse {
    private String email;

    @Builder
    private LoginTestResponse(String email) {
        this.email = email;
    }

    public static LoginTestResponse of(String email) {
        return LoginTestResponse.builder()
                .email(email)
                .build();
    }
}
