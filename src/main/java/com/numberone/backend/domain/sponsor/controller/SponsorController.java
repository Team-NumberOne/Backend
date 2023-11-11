package com.numberone.backend.domain.sponsor.controller;

import com.numberone.backend.domain.sponsor.dto.response.SponsorHomeResponse;
import com.numberone.backend.domain.sponsor.dto.response.SponsorResponse;
import com.numberone.backend.domain.sponsor.service.SponsorService;
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

    @GetMapping("/{sponsorId}")
    public ResponseEntity<SponsorResponse> getSponsorDetail(@PathVariable Long sponsorId){
        return ResponseEntity.ok(sponsorService.getSponsorDetail(sponsorId));
    }

    @GetMapping("/latest")
    public ResponseEntity<SponsorHomeResponse> getSponsorHomeLatest(Authentication authentication){
        return ResponseEntity.ok(sponsorService.getSponsorHomeLatest(authentication.getName()));
    }

    @GetMapping("/popular")
    public ResponseEntity<SponsorHomeResponse> getSponsorHomePopular(Authentication authentication){
        return ResponseEntity.ok(sponsorService.getSponsorHomePopular(authentication.getName()));
    }
}
