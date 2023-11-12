package com.numberone.backend.domain.admin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numberone.backend.domain.admin.dto.response.GetAddressResponse;
import com.numberone.backend.domain.shelter.dto.response.GetAllSheltersResponse;
import com.numberone.backend.domain.shelter.repository.ShelterRepository;
import com.numberone.backend.support.s3.S3Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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

    public String uploadAllShelterInfo() {
        String filePath = "";
        try {
            List<GetAllSheltersResponse> result = shelterRepository.findAllSheltersGroupByRegions();
            String jsonResult = objectMapper.writeValueAsString(result);
            InputStream inputStream = new ByteArrayInputStream(jsonResult.getBytes());
            log.info("[파일 업로드 완료]");
            filePath = s3Provider.uploadFile(databaseUploadPath + "/rawdata", inputStream);
        } catch (Exception e) {
            log.error("Shelter database 파일 생성 중 error 발생 {}", e.getMessage());
        }
        return filePath;
    }

    public String uploadAllAddressInfo() {
        String filePath = "";
        try {
            List<GetAddressResponse> result = shelterRepository.getAllAddressList();
            String jsonResult = objectMapper.writeValueAsString(result);
            InputStream inputStream = new ByteArrayInputStream(jsonResult.getBytes());
            log.info("[파일 업로드 완료]");
            filePath = s3Provider.uploadFile(databaseUploadPath + "/address", inputStream);
        } catch (Exception e) {
            log.error("Shelter database 파일 생성 중 error 발생 {}", e.getMessage());
        }
        return filePath;
    }

    public List<GetAddressResponse> getAllAddressInfo(){
        return shelterRepository.getAllAddressList();
    }

}
