package com.numberone.backend.domain.disaster.dto.response;

import com.numberone.backend.domain.disaster.entity.Disaster;
import com.numberone.backend.domain.disaster.DisasterType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LatestDisasterResponse {
    @Schema(defaultValue = "true")
    private Boolean isExist;
    @Schema(defaultValue = "화재")
    private String disasterType;
    @Schema(defaultValue = "서울특별시 강남구 동작동 화재 발생")
    private String title;
    @Schema(defaultValue = "금일 10.23. 19:39경 소촌동 855 화재 발생, 인근주민은 안전유의 및 차량우회바랍니다. 960-8222")
    private String msg;
    @Schema(defaultValue = "서울특별시 강남구 ・ 오후 2시 46분")
    private String info;

    public static LatestDisasterResponse of(Disaster disaster) {
        String category, time;
        if (disaster.getDisasterType() == DisasterType.OTHERS)
            category = "상황";
        else
            category = disaster.getDisasterType().getDescription();
        time = disaster.getGeneratedAt().format(DateTimeFormatter.ofPattern("a h시 m분", Locale.KOREAN));
        return LatestDisasterResponse.builder()
                .isExist(true)
                .disasterType(disaster.getDisasterType().getDescription())
                .title(disaster.getLocation() + " " + category + " 발생")
                .msg(disaster.getMsg())
                .info(disaster.getLocation() + " ・ " + time)
                .build();
    }

    public static LatestDisasterResponse notExist() {
        return LatestDisasterResponse.builder()
                .isExist(false)
                .build();
    }
}
