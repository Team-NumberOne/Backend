package com.numberone.backend.domain.member.dto.response;

import com.numberone.backend.domain.member.entity.Member;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberIdResponse {
    private Long memberId;
    private String nickname;
    private String realname;

    public static MemberIdResponse of(Member member) {
        return MemberIdResponse.builder()
                .memberId(member.getId())
                .nickname(member.getNickName())
                .realname(member.getRealName())
                .build();
    }
}
