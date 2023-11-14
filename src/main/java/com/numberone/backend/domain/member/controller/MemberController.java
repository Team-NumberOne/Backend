package com.numberone.backend.domain.member.controller;

import com.numberone.backend.domain.member.dto.request.OnboardingRequest;
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


@Tag(name = "members", description = "사용자 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "온보딩시 사용자 초기 데이터 설정하기", description = """
            온보딩에서 선택한 닉네임, 재난유형, 알림지역 데이터를 body에 담아 전달해주세요.
            """)
    @PostMapping("/onboarding")
    public void initMemberData(Authentication authentication, @Valid @RequestBody OnboardingRequest onboardingRequest){
        memberService.initMemberData(authentication.getName(), onboardingRequest);
    }
}
