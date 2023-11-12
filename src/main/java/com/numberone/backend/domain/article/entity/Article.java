package com.numberone.backend.domain.article.entity;

import com.numberone.backend.config.basetime.BaseTimeEntity;
import com.numberone.backend.domain.comment.entity.CommentEntity;
import com.numberone.backend.domain.articleparticipant.entity.ArticleParticipant;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Comment("동네생활 게시글 정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "ARTICLE")
public class Article extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @OneToMany(mappedBy =  "article", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy =  "article", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ArticleParticipant> articleParticipants = new ArrayList<>();

    @Comment("게시글 제목")
    private String title;

    @Comment("게시글 내용")
    private String content;

    @Comment("게시글 태그 (일상:LIFE, 사기:FRAUD, 치안:SAFETY, 제보:REPORT)")
    @Enumerated(EnumType.STRING)
    private ArticleTag articleTag;

    @Comment("게시글 작성 당시 주소")
    private String address;

    @Comment("게시글 좋아요 개수")
    private Integer likeCount; // todo: 동시성 처리
}
