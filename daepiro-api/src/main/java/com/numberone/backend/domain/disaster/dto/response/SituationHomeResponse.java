package com.numberone.backend.domain.disaster.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SituationHomeResponse {
    private List<SituationResponse> situations = new ArrayList<>();

    public static SituationHomeResponse of(List<SituationResponse> situations) {
        return SituationHomeResponse.builder()
                .situations(situations)
                .build();
    }
}
