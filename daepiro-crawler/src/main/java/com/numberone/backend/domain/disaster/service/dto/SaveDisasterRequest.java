package com.numberone.backend.domain.disaster.service.dto;

import com.numberone.backend.domain.disaster.DisasterType;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SaveDisasterRequest {

    private DisasterType disasterType;
    private String location;
    private String msg;
    private Long disasterNum;
    private String createdAt;

    public static SaveDisasterRequest of(DisasterType disasterType, String location, String msg, Long disasterNum, String createdAt) {
        return SaveDisasterRequest.builder()
                .disasterType(disasterType)
                .location(location)
                .msg(msg)
                .disasterNum(disasterNum)
                .createdAt(createdAt)
                .build();
    }
}
