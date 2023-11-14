package com.numberone.backend.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnboardingAddress {
    @Schema(defaultValue = "서울특별시")
    private String lv1;

    @Schema(defaultValue = "영등포구")
    private String lv2;

    @Schema(defaultValue = "신길동")
    private String lv3;
}
