package com.numberone.backend.domain.shelter.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetAllSheltersResponse {

    AddressDetail addressDetail;

    List<ShelterDetail> floods;
    List<ShelterDetail> civils;
    List<ShelterDetail> earthquakes;

    public static GetAllSheltersResponse of(
            AddressDetail address, List<ShelterDetail> floods, List<ShelterDetail> civils, List<ShelterDetail> earthquakes) {
        return GetAllSheltersResponse.builder()
                .addressDetail(address)
                .floods(floods)
                .civils(civils)
                .earthquakes(earthquakes)
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ShelterDetail {
        private Long id;
        private String fullAddress;
        private String facilityFullName;
        private Double longitude;
        private Double latitude;

        @QueryProjection
        public ShelterDetail(Long id, String fullAddress, String facilityFullName, Double longitude, Double latitude) {
            this.id = id;
            this.fullAddress = fullAddress;
            this.facilityFullName = facilityFullName;
            this.longitude = longitude;
            this.latitude = latitude;
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddressDetail {
        private String city;
        private String district;
        private String dong;

        @QueryProjection
        public AddressDetail(String city, String district, String dong) {
            this.city = city;
            this.district = district;
            this.dong = dong;
        }
    }

}
