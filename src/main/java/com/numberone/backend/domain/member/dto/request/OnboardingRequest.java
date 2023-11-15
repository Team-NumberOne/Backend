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
    @NotNull(message = "nickname은 null일 수 없습니다.")
    private String nickname;

    @NotNull(message = "fcm token은 null일 수 없습니다.")
    private String fcmToken;

    private List<OnboardingAddress> addresses;

    private List<OnboardingDisasterType> disasterTypes;
}
