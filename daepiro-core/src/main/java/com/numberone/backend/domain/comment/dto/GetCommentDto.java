package com.numberone.backend.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numberone.backend.domain.comment.entity.CommentEntity;
import com.numberone.backend.domain.member.entity.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetCommentDto {

    private Long commentId;
    private Long parentCommentId;
    private List<GetCommentDto> childComments = new ArrayList<>();
    private Integer likeCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;
    private String content;
    private Long authorId;
    private String authorNickName;
    private String authorProfileImageUrl;
    private boolean isLiked;

    @QueryProjection
    public GetCommentDto(CommentEntity comment){
        if(!Objects.isNull(comment.getParent())){
            this.parentCommentId = comment.getParent().getId();
        }
        this.commentId = comment.getId();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.content = comment.getContent();
        this.authorId = comment.getAuthorId();
        this.likeCount = comment.getLikeCount();
    }

    public void updateCommentInfo(Optional<Member> author, List<Long> likedCommentIdList){
        author.ifPresentOrElse(
                a -> {
                    this.authorNickName = a.getNickName();
                    this.authorProfileImageUrl = a.getProfileImageUrl();
                },
                () -> {
                    this.authorNickName = "알 수 없는 사용자";
                }
        );
        this.isLiked = likedCommentIdList.contains(commentId);
    }

}
