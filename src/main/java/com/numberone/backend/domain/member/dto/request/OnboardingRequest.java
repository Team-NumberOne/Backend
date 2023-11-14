package com.numberone.backend.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnboardingRequest {
    @Schema(defaultValue = "초롱이")
    @NotNull
    private String nickname;

    private List<OnboardingAddress> addresses;

    private List<OnboardingDisasterType> disasterTypes;
}
