package com.numberone.backend.domain.notification.dto.request;

import com.numberone.backend.domain.notification.dto.parameter.NotificationSearchParameter;
import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NotificationSearchParameterDto {
    Long lastNotificationId;
    Boolean isDisaster;

    public NotificationSearchParameter toParameter(){
        return NotificationSearchParameter.builder()
                .lastNotificationId(lastNotificationId)
                .isDisaster(isDisaster)
                .build();
    }
}
