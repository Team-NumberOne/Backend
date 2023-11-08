package com.numberone.backend.domain.disaster.service;

import com.numberone.backend.domain.disaster.dto.request.LatestDisasterRequest;
import com.numberone.backend.domain.disaster.dto.request.SaveDisasterRequest;
import com.numberone.backend.domain.disaster.dto.response.LatestDisasterResponse;
import com.numberone.backend.domain.disaster.entity.Disaster;
import com.numberone.backend.domain.disaster.repository.DisasterRepository;
import com.numberone.backend.domain.disaster.util.DisasterType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DisasterService {
    private final DisasterRepository disasterRepository;
    public LatestDisasterResponse getLatestDisaster(LatestDisasterRequest latestDisasterRequest) {
        //
        Disaster disaster = null;
        return LatestDisasterResponse.of(disaster);
    }

    @Transactional
    public void save(SaveDisasterRequest saveDisasterRequest){
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
