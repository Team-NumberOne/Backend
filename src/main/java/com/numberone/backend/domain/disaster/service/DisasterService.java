package com.numberone.backend.domain.disaster.service;

import com.numberone.backend.domain.disaster.dto.request.LatestDisasterRequest;
import com.numberone.backend.domain.disaster.dto.request.SaveDisasterRequest;
import com.numberone.backend.domain.disaster.dto.response.LatestDisasterResponse;
import com.numberone.backend.domain.disaster.entity.Disaster;
import com.numberone.backend.domain.disaster.repository.DisasterRepository;
import com.numberone.backend.domain.disaster.util.DisasterType;
import com.numberone.backend.util.LocationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DisasterService {
    private final DisasterRepository disasterRepository;
    private final LocationUtil locationUtil;

    public LatestDisasterResponse getLatestDisaster(LatestDisasterRequest latestDisasterRequest) {
        String address = locationUtil.pos2address(latestDisasterRequest.getLatitude(), latestDisasterRequest.getLongitude());
        LocalDateTime time = LocalDateTime.now().minusDays(1);
        List<Disaster> disasters = disasterRepository.findDisastersInAddressAfterTime(address, time, PageRequest.of(0, 1));
        if (!disasters.isEmpty())
            return LatestDisasterResponse.of(disasters.get(0));
        else
            return LatestDisasterResponse.notExist();
    }

    @Transactional
    public void save(SaveDisasterRequest saveDisasterRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(saveDisasterRequest.getCreatedAt(), formatter);
        Disaster disaster = Disaster.of(
                saveDisasterRequest.getDisasterType(),
                saveDisasterRequest.getLocation(),
                saveDisasterRequest.getMsg(),
                saveDisasterRequest.getDisasterNum(),
                dateTime
        );
        disasterRepository.save(disaster);
    }
}
