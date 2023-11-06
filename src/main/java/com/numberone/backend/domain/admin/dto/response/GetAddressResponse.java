package com.numberone.backend.domain.admin.dto.response;

import com.numberone.backend.domain.shelter.util.ShelterType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetAddressResponse {
    private String fullAddress;
    private String city;
    private String district;
    private String dong;
    private ShelterType shelterType;

    @QueryProjection
    public GetAddressResponse(String fullAddress, String city, String district, String dong, ShelterType shelterType) {
        this.fullAddress = fullAddress;
        this.city = city;
        this.district = district;
        this.dong = dong;
        this.shelterType = shelterType;
    }
}
