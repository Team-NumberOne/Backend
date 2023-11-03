package com.numberone.backend.domain.disaster.controller;

import com.numberone.backend.domain.disaster.dto.request.LatestDisasterRequest;
import com.numberone.backend.domain.disaster.dto.response.LatestDisasterResponse;
import com.numberone.backend.domain.disaster.service.DisasterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "disasters", description = "재난문자 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/disaster")
public class DisasterController {
    private final DisasterService disasterService;

    @Operation(summary = "유저와 관련된 가장 최근 재난 문자 가져오기", description =
            """
                    현재 유저의 GPS 정보와 유저가 등록한 지역을 기준으로 가장 최근 재난 문자에 대한 정보를 가져옵니다.
                    
                    유저의 GPS 정보는 api 요청시에 위도(latitude), 경도(longitude)를 body에 담아 전달해주세요.
                    
                    유저가 등록한 지역은 유저가 인증을 위해 같이 보내야하는 jwt 토큰으로부터 알아서 추출해서 처리할 것입니다. 
                    """)
    @PostMapping("/latest")
    public LatestDisasterResponse getLatestDisaster(@RequestBody LatestDisasterRequest latestDisasterRequest) {
        return disasterService.getLatestDisaster(latestDisasterRequest);
    }
}
