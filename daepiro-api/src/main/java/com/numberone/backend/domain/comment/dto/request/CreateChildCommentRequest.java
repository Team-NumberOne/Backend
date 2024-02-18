package com.numberone.backend.domain.comment.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateChildCommentRequest {
    @NotNull
    private String content;

}
