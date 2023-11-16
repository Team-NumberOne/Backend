package com.numberone.backend.domain.disaster.dto.response;

import com.numberone.backend.domain.conversation.dto.response.GetConversationResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SituationHomeResponse {
    List<SituationResponse> situations = new ArrayList<>();

    public static SituationHomeResponse of(List<SituationResponse> situations) {
        return SituationHomeResponse.builder()
                .situations(situations)
                .build();
    }
}
