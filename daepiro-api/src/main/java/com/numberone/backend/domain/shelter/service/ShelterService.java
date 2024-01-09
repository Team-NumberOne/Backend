package com.numberone.backend.domain.shelter.service;

import com.numberone.backend.domain.shelter.dto.request.NearbyShelterRequest;
import com.numberone.backend.domain.shelter.dto.response.*;
import com.numberone.backend.domain.shelter.repository.ShelterRepository;
import com.numberone.backend.domain.shelter.util.ShelterType;
import com.numberone.backend.exception.notfound.NotFoundShelterException;
import com.numberone.backend.provider.s3.S3Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ShelterService {

    private final ShelterRepository shelterRepository;

    private final S3Provider s3Provider;

    @Value("${storage-path.shelter-database-init-path}")
    private String databaseUploadPath;

    public NearestShelterResponse getNearestShelter(NearbyShelterRequest request) {

        ShelterMapper result;
        if (!Objects.isNull(request.getShelterType())) {
            /* type 이 존재하면 type(대피소 유형 별) 에 따른 쿼리 결과를 반환합니다. */
            result = shelterRepository.findNearestShelterByType(
                            request.getLongitude(),
                            request.getLatitude(),
                            ShelterType.kor2code(request.getShelterType()).toString())
                    .orElseThrow(NotFoundShelterException::new);
            return NearestShelterResponse.of(result);
        }

        /* type 이 null 이면 type(대피소 유형 별) 에 상관없이 쿼리 결과를 반환합니다. */
        result = shelterRepository.findNearestAnyShelter(request.getLongitude(), request.getLatitude())
                .orElseThrow(NotFoundShelterException::new);
        return NearestShelterResponse.of(result);
    }

    public NearbyShelterListResponse getNearbyShelterList(NearbyShelterRequest request) {

        List<ShelterMapper> result;
        if (!Objects.isNull(request.getShelterType())) {
            /* type 이 존재하면 type(대피소 유형 별) 에 따른 쿼리 결과를 반환합니다. */
            result = shelterRepository.findNearbyShelterListByType(
                    request.getLongitude(),
                    request.getLatitude(),
                    ShelterType.kor2code(request.getShelterType()).toString());
            return NearbyShelterListResponse.of(result);
        }

        /* type 이 null 이면 type(대피소 유형 별) 에 상관없이 쿼리 결과를 반환합니다. */
        result = shelterRepository.findNearbyAnyShelterList(request.getLongitude(), request.getLatitude());
        return NearbyShelterListResponse.of(result);
    }

    public GetShelterDatabaseUrlResponse getShelterDatabaseInitUrl(){
        return GetShelterDatabaseUrlResponse.of(s3Provider.getS3FileUrl(databaseUploadPath));
    }
}
