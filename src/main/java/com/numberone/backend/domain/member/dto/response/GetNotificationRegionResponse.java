package com.numberone.backend.domain.member.dto.response;

import com.numberone.backend.domain.notificationregion.entity.NotificationRegion;
import lombok.*;

import java.util.List;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetNotificationRegionResponse {
    List<String> regions;
    public static GetNotificationRegionResponse of (List<NotificationRegion> notificationRegions){
        return GetNotificationRegionResponse.builder()
                .regions(notificationRegions.stream().map(NotificationRegion::getLv2).toList())
                .build();
    }
}
