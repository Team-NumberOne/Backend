package com.numberone.backend.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateSafetyRequest {
    @Schema(defaultValue = "true")
    private Boolean isSafety;
}
