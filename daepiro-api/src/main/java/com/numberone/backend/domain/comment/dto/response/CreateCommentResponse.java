package com.numberone.backend.domain.comment.dto.response;

import com.numberone.backend.domain.comment.entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateCommentResponse {
    private LocalDateTime createdAt;
    private Long commentId;

    public static CreateCommentResponse of (CommentEntity comment){
        return CreateCommentResponse.builder()
                .createdAt(comment.getCreatedAt())
                .commentId(comment.getId())
                .build();
    }

}
