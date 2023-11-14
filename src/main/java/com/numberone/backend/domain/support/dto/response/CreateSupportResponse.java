package com.numberone.backend.domain.support.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CreateSupportResponse {
    @Schema(defaultValue = "true")
    private Boolean success;
    private Long supportId;

    public static CreateSupportResponse of(Long supportId) {
        return CreateSupportResponse.builder()
                .success(true)
                .supportId(supportId)
                .build();
    }

    public static CreateSupportResponse fail() {
        return CreateSupportResponse.builder()
                .success(false)
                .build();
    }
}
