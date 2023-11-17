package com.numberone.backend.domain.member.controller;

import com.numberone.backend.domain.member.dto.request.OnboardingRequest;
import com.numberone.backend.domain.member.dto.request.BuyHeartRequest;
import com.numberone.backend.domain.member.dto.response.GetNotificationRegionResponse;
import com.numberone.backend.domain.member.dto.response.HeartCntResponse;
import com.numberone.backend.domain.member.dto.response.UploadProfileImageResponse;
import com.numberone.backend.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Tag(name = "members", description = "사용자 관련 API")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원 프로필 사진 업로드 API",
            description = """
                    1.  반드시 access token 을 헤더에 포함하여 호출해주세요. (유저를 식별하기 위함입니다.)
                                        
                    2.  프로필 사진은 MultipartFile 으로 반드시 image 라는 이름으로 보내주세요
                    """)
    @PostMapping("/profile-image")
    public ResponseEntity<UploadProfileImageResponse> uploadMemberProfileImage(@RequestPart("image") MultipartFile image) {
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
    public void initMemberData(Authentication authentication, @Valid @RequestBody OnboardingRequest onboardingRequest) {
        memberService.initMemberData(authentication.getName(), onboardingRequest);
    }

    @Operation(summary = "사용자가 온보딩 시 추가한 지역 리스트 가져오기", description = """
            게시글 커뮤니티 지역 구분으로 사용할 수 있습니다.
            """)
    @GetMapping("/regions")
    public ResponseEntity<GetNotificationRegionResponse> getNotificationRegions() {
        return ResponseEntity.ok(memberService.getNotificationRegionLv2());
    }
}
