package com.numberone.backend.domain.friendship.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numberone.backend.domain.friendship.entity.Friendship;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InviteFriendResponse {
    private Long invitingMemberId;
    private Long invitedMemberId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss a", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public static InviteFriendResponse of(Friendship friendship) {
        return InviteFriendResponse.builder()
                .invitingMemberId(friendship.getMember().getId())
                .invitedMemberId(friendship.getFriend().getId())
                .createdAt(friendship.getCreatedAt())
                .build();
    }
}
