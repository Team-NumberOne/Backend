package com.numberone.backend.domain.shelter;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
@Embeddable
public class Address {
    @Comment("(구) 주소")
    private String fullAddress;

    @Comment("행정시")
    private String city;

    @Comment("행정구")
    private String district;

    @Comment("행정동(읍/면)")
    private String dong;

    @Comment("도로명 주소")
    private String roadNameAddress;

    @Comment("도로명 우편번호")
    private String roadNamePostalCode;

    @Comment("EPSG:2097 좌표 정보(x)")
    private Double coordinateX;

    @Comment("EPSG:2097 좌표 정보(y)")
    private Double coordinateY;

    @Comment("위도")
    private Double latitude;

    @Comment("경도")
    private Double longitude;

}
