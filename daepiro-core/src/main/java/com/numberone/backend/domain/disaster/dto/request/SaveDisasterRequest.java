package com.numberone.backend.domain.disaster.dto.request;

import com.numberone.backend.domain.disaster.util.DisasterType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SaveDisasterRequest {
    @Schema(defaultValue = "HEAVY_RAIN")
    private DisasterType disasterType;
    @Schema(defaultValue = "서울특별시 성동구 성수동")
    private String location;
    @Schema(defaultValue = """
            [서울특별시] 오늘 15시40분 현재 서울지역에 많은 비가 내리고 있습니다.
            산지와 하천 등 위험지역에 접근하지 마시고, 기상정보를 확인하여 대비 바랍니다.
            """)
    private String msg;
    private Long disasterNum;
    @Schema(defaultValue = "2023/11/25 15:40:00")
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
