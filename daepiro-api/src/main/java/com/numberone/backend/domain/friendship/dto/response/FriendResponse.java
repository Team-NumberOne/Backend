package com.numberone.backend.domain.friendship.dto.response;

import com.numberone.backend.domain.member.entity.Member;
import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FriendResponse {
    private Long friendMemberId;
    private String realName;
    private String nickName;
    private String profileImageUrl;
    private String location;
    private Boolean session;
    private Boolean isSafety;

    public static FriendResponse of(Member member) {
        return FriendResponse.builder()
                .friendMemberId(member.getId())
                .realName(member.getRealName())
                .nickName(member.getNickName())
                .profileImageUrl(member.getProfileImageUrl())
                .location(member.getLocation())
                .session(member.getSession())
                .isSafety(member.getIsSafety())
                .build();
    }
}