package com.numberone.backend.domain.article.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numberone.backend.domain.article.entity.ArticleStatus;
import com.numberone.backend.domain.article.entity.ArticleTag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetArticleListResponse {

    private Long id;
    private ArticleTag tag;
    private String title;
    private String content;
    private String address;
    private ArticleStatus articleStatus;
    private Integer articleLikeCount;

    private String ownerNickName;
    private Long ownerId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private String thumbNailImageUrl;
    private Long thumbNailImageId;

    @Setter
    private long commentCount;
    @Setter
    private boolean isLiked;

    @QueryProjection
    public GetArticleListResponse(Long id,
                                  ArticleTag tag,
                                  String title,
                                  String content,
                                  String address,
                                  ArticleStatus articleStatus,
                                  Integer articleLikeCount,
                                  String ownerNickName,
                                  Long ownerId,
                                  LocalDateTime createdAt,
                                  String thumbNailImageUrl,
                                  Long thumbNailImageId) {
        this.id = id;
        this.tag = tag;
        this.title = title;
        this.content = content;
        this.address = address;
        this.articleStatus = articleStatus;
        this.articleLikeCount = articleLikeCount;
        this.ownerNickName = ownerNickName;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
        this.thumbNailImageUrl = thumbNailImageUrl;
        this.thumbNailImageId = thumbNailImageId;
        this.address = Optional.ofNullable(address).map(this::getAddress).orElse(null);
    }

    private String getAddress(String address) {
        if (!address.isEmpty()) {
            String[] tokens = address.split(" ");
            int length = tokens.length;
            return length > 0 ? tokens[length - 1] : "";
        }
        return "";
    }
}
