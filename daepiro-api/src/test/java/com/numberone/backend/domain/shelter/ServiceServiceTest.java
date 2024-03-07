package com.numberone.backend.domain.shelter;

import com.numberone.backend.ServiceIntegrationTestBase;
import com.numberone.backend.domain.shelter.dto.request.NearbyShelterRequest;
import com.numberone.backend.domain.shelter.dto.response.NearbyShelterListResponse;
import com.numberone.backend.domain.shelter.entity.Shelter;
import com.numberone.backend.domain.shelter.repository.ShelterRepository;
import com.numberone.backend.domain.shelter.service.ShelterService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceServiceTest extends ServiceIntegrationTestBase {

    @Autowired
    private ShelterService shelterService;

    @Autowired
    private ShelterRepository shelterRepository;

    @DisplayName("1000 m 이내에 위치한 주변 대피소 리스트를 조회할 수 있습니다.")
    @Test
    void getNearestShelterTest() {
        // given
        shelterRepository.save(Shelter.of(ShelterType.CIVIL_DEFENCE, "1000m 이내의 대피소 민방위대피소", 35.504, 35.5));
        shelterRepository.save(Shelter.of(ShelterType.CIVIL_DEFENCE, "1000m 보다 먼 대피소", 36.504, 36.5));

        // when
        NearbyShelterListResponse response = shelterService.getNearbyShelterList(NearbyShelterRequest.of(35.5, 35.5, "민방위"));

        // then
        Assertions.assertThat(response.getCount()).isEqualTo(1);
    }

}
