package com.numberone.backend.domain.support.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CreateSupportResponse {
    private Long supportId;

    public static CreateSupportResponse of(Long supportId) {
        return CreateSupportResponse.builder()
                .supportId(supportId)
                .build();
    }
}
