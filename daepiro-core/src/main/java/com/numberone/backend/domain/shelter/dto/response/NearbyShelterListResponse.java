package com.numberone.backend.domain.shelter.dto.response;

import lombok.*;

import java.util.List;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NearbyShelterListResponse {
    List<ShelterDto> shelterList;
    Integer count;


    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ShelterDto {
        private Long id;
        private String facilityName;
        private String distance;
        private Double longitude;
        private Double latitude;
    }

    public static NearbyShelterListResponse of(List<ShelterMapper> queryResults) {
        List<ShelterDto> shelterList = queryResults.stream()
                .map(result -> ShelterDto.builder()
                        .id(result.getId())
                        .facilityName(result.getName())
                        .distance(result.getDistance())
                        .longitude(result.getLongitude())
                        .latitude(result.getLatitude())
                        .build())
                .toList();
        return NearbyShelterListResponse.builder()
                .shelterList(shelterList)
                .count(shelterList.size())
                .build();
    }

}
