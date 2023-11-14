package com.numberone.backend.domain.like.entity;

import com.numberone.backend.config.basetime.BaseTimeEntity;
import com.numberone.backend.domain.article.entity.Article;
import com.numberone.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Comment("게시글 좋아요 정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "ARTICLE_LIKE")
public class ArticleLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long articleId;

    public ArticleLike(Member member, Article article){
        this.member = member;
        this.articleId = article.getId();
    }
}
