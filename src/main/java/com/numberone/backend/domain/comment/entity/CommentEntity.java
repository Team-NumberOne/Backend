package com.numberone.backend.domain.comment.entity;

import com.numberone.backend.config.basetime.BaseTimeEntity;
import com.numberone.backend.domain.article.entity.Article;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Comment("동네생활 댓글 정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "COMMENT")
public class CommentEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "article_id")
    private Article article;

    @Comment("댓글 depth {0: 댓글, 1: 대댓글}")
    private Integer depth;

    @Comment("댓글 좋아요 개수")
    private Integer likeCount; // todo: 동시성 처리

    @Comment("댓글 내용")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CommentEntity parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<CommentEntity> childs = new ArrayList<>();

    public CommentEntity(String content, Article article){
        this.depth = 0;
        this.content = content;
        this.article = article;
    }

    public void updateParent(CommentEntity parent){
        this.parent = parent;
    }

}