package com.numberone.backend.domain.article.entity;

import com.numberone.backend.config.basetime.BaseTimeEntity;
import com.numberone.backend.domain.articleimage.entity.ArticleImage;
import com.numberone.backend.domain.articleparticipant.entity.ArticleParticipant;
import com.numberone.backend.domain.comment.entity.CommentEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
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

    @OneToMany(mappedBy = "article", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ArticleParticipant> articleParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ArticleImage> articleImages = new ArrayList<>();

    @Comment("썸네일 이미지 url ID")
    private Long thumbNailImageUrlId;

    @Comment("게시글 제목")
    private String title;

    @Comment("게시글 내용")
    private String content;

    @Comment("게시글 태그 (일상:LIFE, 사기:FRAUD, 치안:SAFETY, 제보:REPORT)")
    @Enumerated(EnumType.STRING)
    private ArticleTag articleTag;

    @Comment("게시글 상태 (ACTIVATED, DELETED)")
    @Enumerated(EnumType.STRING)
    private ArticleStatus articleStatus;

    @Comment("게시글 작성 당시 주소")
    private String address;

    @Comment("시/도")
    private String lv1;

    @Comment("구/군")
    private String lv2;

    @Comment("동/읍/면")
    private String lv3;

    @ColumnDefault("0")
    @Comment("게시글 좋아요 개수")
    private Integer likeCount;

    @Comment("작성자 ID")
    private Long articleOwnerId;

    public Article(String title, String content, Long articleOwnerId, ArticleTag tag) {
        this.title = title;
        this.content = content;
        this.articleOwnerId = articleOwnerId;
        this.articleTag = tag;
        this.articleStatus = ArticleStatus.ACTIVATED;
        this.likeCount = 0;
    }

    public void updateArticleImage(List<ArticleImage> images, Long thumbNailImageUrlId) {
        this.articleImages = images;
        this.thumbNailImageUrlId = thumbNailImageUrlId;
    }

    public void updateArticleStatus(ArticleStatus status) {
        this.articleStatus = status;
    }

    public void modifyArticle(String title, String content, ArticleTag tag) {
        this.title = title;
        this.content = content;
        this.articleTag = tag;
    }

    public void updateAddress(String address) {
        this.address = address;
    }

    public void updateAddressDetail (String[] addressDetails) {
        int length = addressDetails.length;

        this.lv1 = length > 0 ? addressDetails[0] : "";
        this.lv2 = length > 1 ? addressDetails[1] : "";
        this.lv3 = length > 2 ? addressDetails[2] : "";
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

}
