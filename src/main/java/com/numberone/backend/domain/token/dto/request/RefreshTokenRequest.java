package com.numberone.backend.domain.token.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenRequest {
    @NotNull(message = "토큰이 NULL 입니다.")
    @Comment("서버에서 발급받은 Refresh 토큰")
    private String token;
}
