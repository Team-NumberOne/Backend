package com.numberone.backend.domain.support.dto.response;

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
