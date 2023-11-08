package com.numberone.backend.support.notification.dto;

import lombok.*;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmNotificationDto {
    private String title;
    private String body;
    private String imageUrl;

    public static FcmNotificationDto of(String title, String body, String imageUrl) {
        return FcmNotificationDto.builder()
                .title(title)
                .body(body)
                .imageUrl(imageUrl)
                .build();
    }
}
