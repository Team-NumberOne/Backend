package com.numberone.backend.domain.shelter.dto.response;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NearestShelterResponse {
    private Long id;
    private String facilityName;
    private String distance;
    private Double longitude;
    private Double latitude;

    public static NearestShelterResponse of(ShelterMapper queryResult) {
        return NearestShelterResponse.builder()
                .id(queryResult.getId())
                .facilityName(queryResult.getName())
                .distance(queryResult.getDistance())
                .longitude(queryResult.getLongitude())
                .latitude(queryResult.getLatitude())
                .build();
    }
}
