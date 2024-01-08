package com.numberone.backend.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateGpsRequest {
    @Schema(defaultValue = "37.506136")
    @NotNull(message = "위도 정보는 null일 수 없습니다.")
    private Double latitude;

    @Schema(defaultValue = "126.922046")
    @NotNull(message = "경도 정보는 null일 수 없습니다.")
    private Double longitude;
}
