package com.numberone.backend.domain.conversation.dto.response;

import com.numberone.backend.domain.conversation.entity.Conversation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GetConversationResponse {
    @Schema(defaultValue = "67")
    private Long conversationId;

    @Schema(defaultValue = "생존전문가 ・ 5분전")
    private String info;

    @Schema(defaultValue = "강남역 잠겼나요?")
    private String content;

    @Schema(defaultValue = "2")
    private Long like;

    @Schema(defaultValue = "true")
    private Boolean isLiked;

    @Schema(defaultValue = "true")
    private Boolean isEditable;

    private List<GetConversationResponse> childs;

    private static String makeInfo(String nickname, LocalDateTime createdAt) {
        String info = nickname + " ・ ";
        Duration duration = Duration.between(createdAt, LocalDateTime.now());
        if (duration.toSeconds() < 60)
            info += duration.toSeconds() + "초전";
        else if (duration.toMinutes() < 60)
            info += duration.toMinutes() + "분전";
        else
            info += duration.toHours() + "시간전";
        return info;
    }

    public static GetConversationResponse of(Conversation conversation, Long like, Boolean isLiked, Boolean isEditable, List<GetConversationResponse> childs) {
        return GetConversationResponse.builder()
                .conversationId(conversation.getId())
                .like(like)
                .info(makeInfo(conversation.getMember().getNickName(), conversation.getCreatedAt()))
                .content(conversation.getContent())
                .isLiked(isLiked)
                .isEditable(isEditable)
                .childs(childs)
                .build();
    }
}
