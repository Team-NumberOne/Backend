package com.numberone.backend.domain.notification.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendFcmRequest {
    @NotNull(message = "fcmToken 은 null 일 수 없습니다.")
    private String fcmToken;

    private String body;
    private String title;
    private String imageUrl;

}
