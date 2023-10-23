package com.numberone.backend.domain.shelter.entity;

import com.numberone.backend.config.basetime.BaseTimeEntity;
import com.numberone.backend.domain.shelter.util.Address;
import com.numberone.backend.domain.shelter.util.ShelterStatus;
import com.numberone.backend.domain.shelter.util.ShelterType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Comment("대피소 정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "SHELTER")
public class Shelter extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shelter_id")
    private Long id;

    @Comment("관리 코드(행정용)")
    private String districtCode;

    @Comment("관리 번호(행정용)")
    private String managementNumber;

    @Comment("대피소 운영 상태(OPEN/CLOSE)")
    @Enumerated(EnumType.STRING)
    private ShelterStatus status;

    @Embedded
    private Address address;

    @Comment("대피소 유형( CIVIL_DEFENCE: 민방위, FLOOD: 수해, EARTHQUAKE: 지진")
    @Enumerated(EnumType.STRING)
    private ShelterType shelterType;

    @Comment("시설 이름")
    private String facilityFullName;

}
