package com.numberone.backend.domain.admin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numberone.backend.domain.shelter.dto.response.GetAllSheltersResponse;
import com.numberone.backend.domain.shelter.repository.ShelterRepository;
import com.numberone.backend.support.S3Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.List;

@Slf4j
@RestController
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final ShelterRepository shelterRepository;

    private final ObjectMapper objectMapper;

    private final S3Provider s3Provider;

    @Value("${storage-path.shelter-database-init-path}")
    private String databaseUploadPath;

    public void getShelterDatabase() {
        try {
            List<GetAllSheltersResponse> result = shelterRepository.findAllSheltersGroupByRegions();
            String jsonResult = objectMapper.writeValueAsString(result);
            InputStream inputStream = new ByteArrayInputStream(jsonResult.getBytes());
            s3Provider.uploadJsonFile(databaseUploadPath, inputStream);
            log.info("[파일 업로드 완료]");
        } catch (Exception e) {
            log.error("Shelter database 파일 생성 중 error 발생 {}", e.getMessage());
        }
    }

}
