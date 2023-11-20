package com.numberone.backend.domain.disaster.controller;

import com.numberone.backend.domain.disaster.dto.request.LatestDisasterRequest;
import com.numberone.backend.domain.disaster.dto.response.LatestDisasterResponse;
import com.numberone.backend.domain.disaster.dto.response.SituationDetailResponse;
import com.numberone.backend.domain.disaster.dto.response.SituationHomeResponse;
import com.numberone.backend.domain.disaster.service.DisasterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<LatestDisasterResponse> getLatestDisaster(Authentication authentication, @Valid @RequestBody LatestDisasterRequest latestDisasterRequest) {
        return ResponseEntity.ok(disasterService.getLatestDisaster(authentication.getName(), latestDisasterRequest));
    }

    @Operation(summary = "재난상황 커뮤니티 데이터 가져오기", description = """
            재난상황 페이지에서 필요한 재난목록과 그와 관련된 대화(댓글)들을 가져옵니다.
            """)
    @GetMapping("/situation")
    public ResponseEntity<SituationHomeResponse> getSituationHome(Authentication authentication) {
        return ResponseEntity.ok(disasterService.getSituationHome(authentication.getName()));
    }

    @Operation(summary = "해당 재난과 관련된 모든 커뮤니티 대화 가져오기", description = """
            정렬기준(최신순: time, 인기순: popularity) 과 재난상황 id를 파라미터로 전달해주세요.
                        
            커뮤니티-재난상황-댓글더보기 페이지에서 사용하는 API입니다.
            """)
    @GetMapping("/{sort}/{disasterId}")
    public ResponseEntity<SituationDetailResponse> getSituationDetail(Authentication authentication, @PathVariable Long disasterId, @PathVariable String sort) {
        return ResponseEntity.ok(disasterService.getSituationDetail(authentication.getName(), disasterId, sort));
    }
}
