package com.numberone.backend.domain.comment.dto.response;

import com.numberone.backend.domain.comment.entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateChildCommentResponse {

    private LocalDateTime createdAt;
    private Long commentId;

    public static CreateChildCommentResponse of (CommentEntity comment){
        return CreateChildCommentResponse.builder()
                .createdAt(comment.getCreatedAt())
                .commentId(comment.getId())
                .build();
    }

}
