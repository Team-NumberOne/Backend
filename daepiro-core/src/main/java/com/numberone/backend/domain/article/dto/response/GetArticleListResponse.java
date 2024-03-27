package com.numberone.backend.domain.article.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numberone.backend.domain.article.entity.Article;
import com.numberone.backend.domain.article.entity.ArticleStatus;
import com.numberone.backend.domain.article.entity.ArticleTag;
import com.numberone.backend.domain.articleimage.entity.ArticleImage;
import com.numberone.backend.domain.member.entity.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetArticleListResponse { // todo: record

    private ArticleTag tag;
    private Long id;
    private String title;
    private String content;
    private String address;
    @Setter
    private String ownerNickName;
    private Long ownerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    private ArticleStatus articleStatus;
    @Setter
    private String thumbNailImageUrl;
    private Long thumbNailImageId;

    private Integer articleLikeCount;
    @Setter
    private Long commentCount;
    private Boolean isLiked;


    @QueryProjection
    public GetArticleListResponse(Article article, Long ownerId, Long thumbNailImageId) {
        this.tag = article.getArticleTag();
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.ownerId = ownerId;
        this.createdAt = article.getCreatedAt();
        this.articleStatus = article.getArticleStatus();
        this.thumbNailImageId = thumbNailImageId;
        this.articleLikeCount = article.getLikeCount();
        String articleAddress = article.getAddress();
        if(!articleAddress.isEmpty()){
            String[] elements = articleAddress.split(" ");
            switch (elements.length){
                case 3 -> this.address = elements[2];
                case 2 -> this.address = elements[1];
                case 1 -> this.address = elements[0];
                default -> this.address = "";
            }
        } else {
            this.address = "";
        }
    }


    // todo: 파라미터로 optional 을 받지말자.
    public void updateInfo(Optional<Member> owner,
                           Optional<ArticleImage> articleImage,
                           List<Long> memberLikedArticleIdList,
                           Long commentCount ){
        owner.ifPresentOrElse(
                o -> setOwnerNickName(o.getNickName()),
                () -> setOwnerNickName("알 수 없는 사용자")
        );
        articleImage.ifPresentOrElse(
                image -> setThumbNailImageUrl(image.getImageUrl()),
                () -> setThumbNailImageUrl("")
        );
        this.isLiked = memberLikedArticleIdList.contains(id);
        this.commentCount = commentCount;
    }

}
