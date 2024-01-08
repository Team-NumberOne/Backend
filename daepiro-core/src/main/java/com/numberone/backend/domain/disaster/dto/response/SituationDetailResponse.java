package com.numberone.backend.domain.disaster.dto.response;

import com.numberone.backend.domain.conversation.dto.response.GetConversationResponse;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SituationDetailResponse {
    private List<GetConversationResponse> conversations;

    public static SituationDetailResponse of(List<GetConversationResponse> conversations) {
        return SituationDetailResponse.builder()
                .conversations(conversations)
                .build();
    }
}
