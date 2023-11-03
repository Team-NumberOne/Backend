package com.numberone.backend.domain.disaster.service;

import com.numberone.backend.domain.disaster.dto.request.LatestDisasterRequest;
import com.numberone.backend.domain.disaster.dto.response.LatestDisasterResponse;
import com.numberone.backend.domain.disaster.entity.Disaster;
import com.numberone.backend.domain.disaster.util.DisasterType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DisasterService {
    public LatestDisasterResponse getLatestDisaster(LatestDisasterRequest latestDisasterRequest) {
        Disaster disaster = Disaster.of(
                DisasterType.kor2code("화재"),
                2,
                "서울특별시 강남구 동작동 화재 발생",
                "금일 10.23. 19:39경 소촌동 855 화재 발생, 인근주민은 안전유의 및 차량우회바랍니다. 960-8222",
                "서울특별시 강남구 ・ 오후 2시 46분"
        );
        return LatestDisasterResponse.of(disaster);
    }
}
