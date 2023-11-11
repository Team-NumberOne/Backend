package com.numberone.backend.domain.sponsor.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SponsorHomeResponse {
    private Long supporterCnt;
    private List<String> messages = new ArrayList<>();
    private String nickname;
    private List<SponsorResponse> sponsors = new ArrayList<>();

    public static SponsorHomeResponse of(Long supporterCnt, List<String> messages, String nickname, List<SponsorResponse> sponsors) {
        return SponsorHomeResponse.builder()
                .supporterCnt(supporterCnt)
                .messages(messages)
                .nickname(nickname)
                .sponsors(sponsors)
                .build();
    }
}
