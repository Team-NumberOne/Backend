package com.numberone.backend.domain.shelter.dto.request;

import com.numberone.backend.domain.shelter.util.ShelterType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NearbyShelterRequest {
    @Schema(defaultValue = "126.9723")
    @NotNull(message = "경도 정보는 null 일 수 없습니다.")
    private Double longitude;

    @Schema(defaultValue = "37.5559")
    @NotNull(message = "위도 정보는 null 일 수 없습니다.")
    private Double latitude;

    @Schema(defaultValue = "민방위")
    private String shelterType;

    public static NearbyShelterRequest of(Double longitude, Double latitude, String shelterType) {
        return NearbyShelterRequest.builder()
                .longitude(longitude)
                .latitude(latitude)
                .shelterType(shelterType)
                .build();
    }
}
