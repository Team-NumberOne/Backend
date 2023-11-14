package com.numberone.backend.domain.member.dto.response;

import com.numberone.backend.domain.member.entity.Member;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
public class HeartCntResponse {
    private Integer heartCnt;

    public static HeartCntResponse of(Member member) {
        return HeartCntResponse.builder()
                .heartCnt(member.getHeartCnt())
                .build();
    }
}
