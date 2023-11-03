package com.numberone.backend.domain.disaster.dto.request;

import com.numberone.backend.domain.disaster.dto.response.LatestDisasterResponse;
import com.numberone.backend.domain.disaster.entity.Disaster;
import com.numberone.backend.domain.disaster.util.DisasterType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LatestDisasterRequest {
    @Schema(defaultValue = "126.9723")
    @NotNull(message = "경도 정보는 null 일 수 없습니다.")
    private Double longitude;

    @Schema(defaultValue = "37.5559")
    @NotNull(message = "위도 정보는 null 일 수 없습니다.")
    private Double latitude;
}
