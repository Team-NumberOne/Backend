package com.numberone.backend.domain.shelter.service;

import com.numberone.backend.domain.shelter.dto.request.NearbyShelterRequest;
import com.numberone.backend.domain.shelter.dto.response.NearbyShelterListResponse;
import com.numberone.backend.domain.shelter.dto.response.NearestShelterResponse;
import com.numberone.backend.domain.shelter.dto.response.ShelterMapper;
import com.numberone.backend.domain.shelter.repository.ShelterRepository;
import com.numberone.backend.exception.notfound.NotFoundShelterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ShelterService {
    private final ShelterRepository shelterRepository;

    public NearestShelterResponse getNearbyShelter(NearbyShelterRequest request) {
        ShelterMapper result = shelterRepository.findNearestAnyShelter(request.getLongitude(), request.getLatitude())
                .orElseThrow(NotFoundShelterException::new);
        return NearestShelterResponse.of(result);
    }

    public NearbyShelterListResponse getNearbyShelterList(NearbyShelterRequest request) {
        List<ShelterMapper> shelters = shelterRepository.findNearbyAnyShelterList(request.getLongitude(), request.getLatitude());
        if (shelters.isEmpty()) {
            throw new NotFoundShelterException();
        }
        return NearbyShelterListResponse.of(shelters);
    }
}
