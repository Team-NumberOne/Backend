package com.numberone.backend.domain.notification.dto.request;

import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NotificationSearchParameter {
    Long lastNotificationId;
    Boolean isDisaster;
}
