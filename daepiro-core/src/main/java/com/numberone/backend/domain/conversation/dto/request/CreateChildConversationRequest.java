package com.numberone.backend.domain.conversation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateChildConversationRequest {
    @NotNull(message = "댓글 내용은 null일 수 없습니다.")
    @Schema(defaultValue = "강남역 잠겼나요?")
    private String content;
}
