package com.numberone.backend.domain.support.controller;

import com.numberone.backend.domain.support.dto.request.CreateSupportRequest;
import com.numberone.backend.domain.support.dto.request.EditMessageRequest;
import com.numberone.backend.domain.support.dto.response.CreateSupportResponse;
import com.numberone.backend.domain.support.service.SupportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "supports", description = "응원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/support")
public class SupportController {
    private final SupportService supportService;

    @Operation(summary = "응원 메시지 등록하기", description =
            """
                    응원 메시지를 등록하는 api입니다. api 요청시 응원 메시지 내용을 바디에 같이 전달해주세요.
                                        
                    요청 경로는 /api/sponsor/{supportId}입니다.
                                        
                    support Id값은 후원 api 이용시 반환되는 값을 기억했다가 응원 메시지 api 요청시 경로에 같이 전달해주세요.
                    """)
    @PostMapping("/{supportId}")
    public void editMessage(@Valid @RequestBody EditMessageRequest editMessageRequest, @PathVariable Long supportId) {
        supportService.editMessage(editMessageRequest, supportId);
    }

    @Operation(summary = "후원하기", description =
            """
                    후원하는 api입니다.
                                        
                    후원에 대한 id인 sponsor ID와 후원할 마음 갯수를 같이 전달해주세요.
                    """)
    @PostMapping()
    public ResponseEntity<CreateSupportResponse> create(Authentication authentication, @Valid @RequestBody CreateSupportRequest createSupportRequest) {
        CreateSupportResponse createSupportResponse = supportService.create(createSupportRequest, authentication.getName());
        return ResponseEntity.ok(createSupportResponse);
    }
}
