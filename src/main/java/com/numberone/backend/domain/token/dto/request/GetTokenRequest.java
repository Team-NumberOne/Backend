package com.numberone.backend.domain.token.dto.request;

import lombok.*;
import org.hibernate.annotations.Comment;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetTokenRequest {
    @Comment("카카오 또는 네이버에서 발급된 Access 토큰")
    private String token;
}
