package com.numberone.backend.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberIdResponse {
    private Long memberId;

    public static MemberIdResponse of(long memberId){
        return MemberIdResponse.builder()
                .memberId(memberId)
                .build();
    }
}
