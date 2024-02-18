package com.numberone.backend.domain.admin.controller;

import com.numberone.backend.domain.admin.dto.SaveAdminDisasterRequest;
import com.numberone.backend.domain.admin.dto.response.GetAddressResponse;
import com.numberone.backend.domain.admin.service.AdminService;
import com.numberone.backend.domain.disaster.service.DisasterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final DisasterService disasterService;

    @Operation(summary = "서버에 지역별 대피소 정보 Json 파일로 업로드하기", description =
            """
                                
                    🔥 (주의) Shelter Database 정보를 json 형태로 서버 스토리지에 저장하는 기능으로, 10 분 이상 소요됩니다.
                                
                    요청 시, 현재 저장된 대피소 db 를 기반으로
                                
                    지역 별 대피소 정보를 Json 형태로 정리하여 서버 스토리지에 저장합니다.
                                
                    대피소 db 를 업데이트 한 경우에, 실행하는 api 입니다. 
                                
                    """)
    @PostMapping("/shelter-init")
    public ResponseEntity<String> uploadAllShelterInfo() {
        return ResponseEntity.created(URI.create("/api/admin/shelter-init"))
                .body(adminService.uploadAllShelterInfo());
    }

    @Operation(summary = "서버에 대피소 주소 정보 Json 파일로 업로드하기", description =
            """
                                
                    🔥 (주의) Shelter Database 정보를 기반으로 주소 정보를 json 형태로 서버 스토리지에 저장하는 기능입니다.
                                
                    대피소 db 를 업데이트 한 경우에, 실행하는 api 입니다. 
                                
                    """)
    @PostMapping("/address-info")
    public ResponseEntity<String> uploadAllAddressInfo() {
        return ResponseEntity.created(URI.create("/api/admin/address-info"))
                .body(adminService.uploadAllAddressInfo());
    }

    @Operation(summary = "주소 정보 Json 파일로 조회하기")
    @GetMapping("/address-info")
    public ResponseEntity<List<GetAddressResponse>> getAllAddressInfo() {
        return ResponseEntity.ok(adminService.getAllAddressInfo());
    }

    @Operation(summary = "(테스트용) 재난 발생시키기", description = """
            지원되는 재난 유형:
                        
                        
                DROUGHT("가뭄"),
                STRONG_WIND("강풍"),
                DRYNESS("건조"),
                HEAVY_SNOWFALL("대설"),
                TIDAL_WAVE("대조기"),
                FINE_DUST("미세먼지"),
                WILDFIRE("산불"),
                LANDSLIDE("산사태"),
                FOG("안개"),
                EARTHQUAKE("지진"),
                TYPHOON("태풍"),
                HEATWAVE("폭염"),
                ROUGH_SEA("풍랑"),
                COLD_WAVE("한파"),
                HEAVY_RAIN("호우"),
                FLOOD("홍수"),
                        
                GAS("가스"),
                TRAFFIC("교통"),
                FINANCE("금융"),
                COLLAPSE("붕괴"),
                WATER_SUPPLY("수도"),
                ENERGY("에너지"),
                MEDICAL("의료"),
                INFECTIOUS_DISEASE("전염병"),
                POWER_OUTAGE("정전"),
                COMMUNICATION("통신"),
                EXPLOSION("폭발"),
                FIRE("화재"),
                ENVIRONMENTAL_POLLUTION("환경오염사고"),
                AI("AI"),
                        
                EMERGENCY("비상사태"),
                TERROR("테러"),
                CHEMICAL("화생방사고"),
                        
                MISSING("실종"),
                OTHERS("기타"),
            """)
    @PostMapping("/disaster")
    public ResponseEntity<SaveAdminDisasterRequest> createDisaster(@RequestBody SaveAdminDisasterRequest request) {
        disasterService.save(request);
        return ResponseEntity.ok(request);
    }

}
