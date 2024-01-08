package com.numberone.backend.domain.disaster.dto.response;

import com.numberone.backend.domain.conversation.dto.response.GetConversationResponse;
import com.numberone.backend.domain.disaster.entity.Disaster;
import com.numberone.backend.domain.disaster.util.DisasterType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SituationResponse {
    @Schema(defaultValue = "32")
    private Long disasterId;

    @Schema(defaultValue = "화재")
    private String disasterType;

    @Schema(defaultValue = "서울특별시 강남구 동작동 화재 발생")
    private String title;

    @Schema(defaultValue = "많은 비가 예상되므로 반지하주택, 지하주차장, 지하차도, 지하공간 등 침수가 우려되니 사전점검 등 산사태 위험지역 주민은 이상 징후 시 대피 바랍니다.")
    private String msg;

    @Schema(defaultValue = "서울특별시 강남구 ・ 오후 2시 46분")
    private String info;

    @Schema(defaultValue = "6")
    private Long conversationCnt;

    private List<GetConversationResponse> conversations;


    public static SituationResponse of(Disaster disaster, List<GetConversationResponse> conversations, Long conversationCnt) {
        String category, time;
        if (disaster.getDisasterType() == DisasterType.OTHERS)
            category = "상황";
        else
            category = disaster.getDisasterType().getDescription();
        time = disaster.getGeneratedAt().format(DateTimeFormatter.ofPattern("a h시 m분", Locale.KOREAN));
        return SituationResponse.builder()
                .disasterId(disaster.getId())
                .disasterType(disaster.getDisasterType().getDescription())
                .title(disaster.getLocation() + " " + category + " 발생")
                .msg(disaster.getMsg())
                .info(disaster.getLocation() + " ・ " + time)
                .conversations(conversations)
                .conversationCnt(conversationCnt)
                .build();
    }
}
