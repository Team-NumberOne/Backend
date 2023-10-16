package com.numberone.backend.domain.shelter.controller;

import com.numberone.backend.domain.shelter.dto.request.NearbyShelterRequest;
import com.numberone.backend.domain.shelter.dto.response.NearbyShelterListResponse;
import com.numberone.backend.domain.shelter.dto.response.NearestShelterResponse;
import com.numberone.backend.domain.shelter.service.ShelterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "shelters", description = "대피소 관련 API")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/shelters")
@RestController
public class ShelterController {
    private final ShelterService shelterService;

    @Operation(summary = "가장 가까운 대피소 정보 하나 가져오기", description =
            """
            위도 경도를 body 에 담아서 post 요청 해주세요.
            
            가장 가까운 대피소 정보를 반환합니다.
            
            distance 는 미터(m) 단위이며 1500 m 이내 대피소만 검색합니다.
            
            검색 결과가 0 개인 경우, NotFound 예외를 터뜨립니다.
            
            access token 을 헤더에 담아서 요청해주세요.
                    
            """)
    @PostMapping
    public ResponseEntity<NearestShelterResponse> getNearestShelter(
            @RequestBody @Valid NearbyShelterRequest request) {
        return ResponseEntity.ok(shelterService.getNearbyShelter(request));
    }

    @Operation(summary = "가까운 대피소 리스트 가져오기", description =
            """
            위도 경도를 body 에 담아서 post 요청 해주세요.
            
            가까운 대피소 정보를 거리 순으로 정렬하여 반환합니다.
            
            count 는 대피소 개수이며,
            
            최대 10 개 까지만 반환합니다.
            
            distance 는 미터(m) 단위이며 1500 m 이내 대피소만 검색합니다.
            
            검색 결과가 0 개인 경우, NotFound 예외를 터뜨립니다.
                    
            access token 을 헤더에 담아서 요청해주세요.
            """)
    @PostMapping("/list")
    public ResponseEntity<NearbyShelterListResponse> getNearbyShelterList(
            @RequestBody @Valid NearbyShelterRequest request) {
        return ResponseEntity.ok(shelterService.getNearbyShelterList(request));
    }
}
