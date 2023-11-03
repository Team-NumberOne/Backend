package com.numberone.backend.domain.disaster.dto.response;

import com.numberone.backend.domain.disaster.entity.Disaster;
import com.numberone.backend.domain.disaster.util.DisasterType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LatestDisasterResponse {
    @Schema(defaultValue = "화재")
    private String disasterType;
    @Schema(defaultValue = "2")
    private Integer severity;
    @Schema(defaultValue = "서울특별시 강남구 동작동 화재 발생")
    private String title;
    @Schema(defaultValue = "금일 10.23. 19:39경 소촌동 855 화재 발생, 인근주민은 안전유의 및 차량우회바랍니다. 960-8222")
    private String msg;
    @Schema(defaultValue = "서울특별시 강남구 ・ 오후 2시 46분")
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
