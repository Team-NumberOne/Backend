package com.numberone.backend.domain.comment.dto.response;

import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DeleteCommentResponse {
    private Long commentId;
}
