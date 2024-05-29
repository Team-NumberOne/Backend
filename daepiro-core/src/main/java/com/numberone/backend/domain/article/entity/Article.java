package com.numberone.backend.domain.article.entity;

import com.numberone.backend.config.basetime.BaseTimeEntity;
import com.numberone.backend.domain.articleimage.entity.ArticleImage;
import com.numberone.backend.domain.comment.entity.CommentEntity;
import com.numberone.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Comment("동네생활 게시글 정보")
@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ARTICLE")
public class Article extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @OneToMany(mappedBy = "article", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_owner_id")
     private Member articleOwner;

    public static Article of(String title, String content, Member owner, ArticleTag tag){
        return Article.builder()
                .title(title)
                .content(content)
                .articleOwner(owner)
                .articleTag(tag)
                .articleStatus(ArticleStatus.ACTIVATED)
                .likeCount(0)
                .build();
    }

    public void updateArticleStatus(ArticleStatus status) {
        this.articleStatus = status;
    }

    public void updateThumbNailImageUrlId(Long thumbNailImageUrlId){
        this.thumbNailImageUrlId = thumbNailImageUrlId;
    }

    public void modify(String title, String content, ArticleTag tag) {
        this.title = title;
        this.content = content;
        this.articleTag = tag;
    }

    public void updateAddress(String address) {
        this.address = address;

        String [] tokens = address.split(" ");
        int length = tokens.length;

        this.lv1 = length > 0 ? tokens[0] : "";
        this.lv2 = length > 1 ? tokens[1] : "";
        this.lv3 = length > 2 ? tokens[2] : "";
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
