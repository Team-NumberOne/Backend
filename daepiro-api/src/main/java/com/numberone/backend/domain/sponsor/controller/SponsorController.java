package com.numberone.backend.domain.sponsor.controller;

import com.numberone.backend.domain.sponsor.dto.response.SponsorHomeResponse;
import com.numberone.backend.domain.sponsor.dto.response.SponsorResponse;
import com.numberone.backend.domain.sponsor.service.SponsorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "sponsors", description = "후원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sponsor")
public class SponsorController {
    private final SponsorService sponsorService;

    @Operation(summary = "후원 단일 데이터 가져오기", description =
            """
                    후원 한개의 데이터를 가져오는 API입니다.
                                        
                    후원 세부 페이지에서 사용하는 API입니다.
                    
                    후원 목록 가져오기 API에서 반환된 응답에서 얻은 후원 id중 가져오고 싶은 후원의 id를 같이 전달해주세요.
                    """)
    @GetMapping("/{sponsorId}")
    public ResponseEntity<SponsorResponse> getSponsorDetail(@PathVariable Long sponsorId){
        return ResponseEntity.ok(sponsorService.getSponsorDetail(sponsorId));
    }

    @Operation(summary = "후원 목록 최신순 가져오기", description =
            """
                    후원 목록을 최신순으로 가져오는 API입니다.
                                        
                    또한 후원 목록 페이지에서 필요한 총 후원한 사람들의 수(supporterCnt)와 사람들이 입력한 응원메시지(messages)가 같이 전달됩니다.
                    
                    후원 목록 가져오기 API에서 반환된 응답에서 얻은 후원 id중 가져오고 싶은 후원의 id를 같이 전달해주세요.
                    """)
    @GetMapping("/latest")
    public ResponseEntity<SponsorHomeResponse> getSponsorHomeLatest(){
        return ResponseEntity.ok(sponsorService.getSponsorHomeLatest());
    }

    @Operation(summary = "후원 목록 인기순 가져오기", description =
            """
                    후원 목록을 인기순으로 가져오는 API입니다.
                                        
                    또한 후원 목록 페이지에서 필요한 총 후원한 사람들의 수(supporterCnt)와 사람들이 입력한 응원메시지(messages)가 같이 전달됩니다.
                    
                    후원 목록 가져오기 API에서 반환된 응답에서 얻은 후원 id중 가져오고 싶은 후원의 id를 같이 전달해주세요.
                    """)
    @GetMapping("/popular")
    public ResponseEntity<SponsorHomeResponse> getSponsorHomePopular(){
        return ResponseEntity.ok(sponsorService.getSponsorHomePopular());
    }
}
