package com.numberone.backend.domain.member.controller;

import com.numberone.backend.domain.member.dto.request.OnboardingRequest;
import com.numberone.backend.domain.member.dto.request.BuyHeartRequest;
import com.numberone.backend.domain.member.dto.response.HeartCntResponse;
import com.numberone.backend.domain.member.dto.response.UploadProfileImageResponse;
import com.numberone.backend.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@Tag(name = "members", description = "사용자 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/profile-image")
    public ResponseEntity<UploadProfileImageResponse> uploadMemberProfileImage(@RequestPart MultipartFile image){
        return ResponseEntity.created(URI.create("/api/members/profile-image"))
                .body(memberService.uploadProfileImage(image));
    }

    @PostMapping("/heart")
    @Operation(summary = "마음 구입하기", description = """
            구입한 마음 갯수를 body에 담아 전달해주세요.
                        
            response 에는 구입한 후에 사용자의 현재 마음 갯수가 저장되어 있습니다.
            """)
    public ResponseEntity<HeartCntResponse> buyHeart(@RequestBody @Valid BuyHeartRequest buyHeartRequest, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.buyHeart(buyHeartRequest, authentication.getName()));
    }

    @GetMapping("/heart")
    @Operation(summary = "사용자의 현재 마음 갯수 가져오기", description = """
            사용자의 현재 마음 갯수가 response로 전달됩니다.
            """)
    public ResponseEntity<HeartCntResponse> getHeart(Authentication authentication) {
        return ResponseEntity.ok(memberService.getHeart(authentication.getName()));
    }

    @Operation(summary = "온보딩시 사용자 초기 데이터 설정하기", description = """
            온보딩에서 선택한 닉네임, 재난유형, 알림지역 데이터를 body에 담아 전달해주세요.
            """)
    @PostMapping("/onboarding")
    public void initMemberData(Authentication authentication, @Valid @RequestBody OnboardingRequest onboardingRequest){
        memberService.initMemberData(authentication.getName(), onboardingRequest);
    }
}
