package com.numberone.backend.domain.articleimage.entity;

import com.numberone.backend.domain.article.entity.Article;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Comment("동네생활 게시글 이미지 정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "ARTICLE_IMAGE")
public class ArticleImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "article_id")
    private Article article;

    @Comment("동네생활 게시글 이미지 URL")
    private String imageUrl;


    public ArticleImage(Article article, String imageUrl){
        this.article = article;
        this.imageUrl = imageUrl;
    }
}
