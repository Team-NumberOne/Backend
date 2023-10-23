package com.numberone.backend.domain.admin.controller;

import com.numberone.backend.domain.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "서버에 지역별 대피소 정보 Json 파일로 업로드하기", description =
            """
            
            🔥 (주의) Shelter Database 정보를 json 형태로 요약하여 서버 스토리지에 저장하는 기능으로, 10 분 이상 소요됩니다.
            
            요청 시, 현재 저장된 대피소 db 를 기반으로
            
            지역 별 대피소 정보를 Json 형태로 정리하여 서버 스토리지에 저장합니다.
            
            대피소 db 를 업데이트 한 경우에, 실행하는 api 입니다. 
            
            """)
    @GetMapping("/shelter-init")
    public ResponseEntity<String> CreateShelterDatabaseJsonFile() {
        return ResponseEntity.created(URI.create("/api/admin"))
                .body(adminService.uploadShelterJsonFile());
    }
}
