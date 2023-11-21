package com.numberone.backend.domain.disaster.dto.request;

import com.numberone.backend.domain.disaster.util.DisasterType;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(defaultValue = "2023/11/21 23:22:00")
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
