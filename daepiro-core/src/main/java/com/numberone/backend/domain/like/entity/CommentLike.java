package com.numberone.backend.domain.like.entity;

import com.numberone.backend.config.basetime.BaseTimeEntity;
import com.numberone.backend.domain.comment.entity.CommentEntity;
import com.numberone.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Comment("댓글 좋아요 정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "COMMENT_LIKE")
public class CommentLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long commentId;

    @Builder
    private CommentLike(Member member, CommentEntity comment) {
        this.member = member;
        this.commentId = comment.getId();
    }

    public static CommentLike of(Member member, CommentEntity comment) {
        return CommentLike.builder()
                .member(member)
                .comment(comment)
                .build();
    }
}
