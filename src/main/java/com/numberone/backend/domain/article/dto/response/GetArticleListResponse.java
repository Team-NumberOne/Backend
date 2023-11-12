package com.numberone.backend.domain.article.dto.response;

import com.numberone.backend.domain.article.entity.Article;
import com.numberone.backend.domain.article.entity.ArticleStatus;
import com.numberone.backend.domain.article.entity.ArticleTag;
import com.numberone.backend.domain.articleimage.entity.ArticleImage;
import com.numberone.backend.domain.member.entity.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetArticleListResponse {

    private ArticleTag tag;
    private Long id;
    private String title;
    private String content;
    private String address;
    private String ownerNickName;
    private Long ownerId;
    private LocalDateTime createdAt;
    private ArticleStatus articleStatus;
    private String thumbNailImageUrl;
    private Long thumbNailImageId;

    private Integer articleLikeCount;
    private Integer commentCount;


    @QueryProjection
    public GetArticleListResponse(Article article, Long ownerId, Long thumbNailImageId) {
        this.tag = article.getArticleTag();
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.address = article.getAddress();
        this.ownerId = ownerId;
        this.createdAt = article.getCreatedAt();
        this.articleStatus = article.getArticleStatus();
        this.thumbNailImageId = thumbNailImageId;
        this.articleLikeCount = article.getLikeCount();
        this.commentCount = article.getCommentCount();
    }

    public void setOwnerNickName(String nickName){
        this.ownerNickName = nickName;
    }

    public void setThumbNailImageUrl(String thumbNailImageUrl){
        this.thumbNailImageUrl = thumbNailImageUrl;
    }

    public void updateInfo(Optional<Member> owner, Optional<ArticleImage> articleImage){
        owner.ifPresentOrElse(
                o -> setOwnerNickName(o.getNickName()),
                () -> setOwnerNickName("알 수 없는 사용자")
        );
        articleImage.ifPresentOrElse(
                image -> setThumbNailImageUrl(image.getImageUrl()),
                () -> setThumbNailImageUrl("")
        );
    }


}
