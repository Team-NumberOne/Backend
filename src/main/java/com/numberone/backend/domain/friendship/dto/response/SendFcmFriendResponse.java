package com.numberone.backend.domain.friendship.dto.response;

import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SendFcmFriendResponse {
    private String title;
    private String body;
}
