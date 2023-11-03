package com.numberone.backend.domain.disaster.dto.response;

import com.numberone.backend.domain.disaster.entity.Disaster;
import com.numberone.backend.domain.disaster.util.DisasterType;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LatestDisasterResponse {
    private String disasterType;
    private Integer severity;
    private String title;
    private String msg;
    private String info;

    public static LatestDisasterResponse of(Disaster disaster) {
        return LatestDisasterResponse.builder()
                .disasterType(disaster.getDisasterType().getDescription())
                .severity(disaster.getSeverity())
                .title(disaster.getTitle())
                .msg(disaster.getMsg())
                .info(disaster.getInfo())
                .build();
    }
}
