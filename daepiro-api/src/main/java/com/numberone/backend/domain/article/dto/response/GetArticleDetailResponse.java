package com.numberone.backend.domain.article.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numberone.backend.domain.article.entity.Article;
import com.numberone.backend.domain.article.entity.ArticleTag;
import com.numberone.backend.domain.articleimage.entity.ArticleImage;
import com.numberone.backend.domain.member.entity.Member;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Builder
public record GetArticleDetailResponse(
        Long articleId,
        Integer likeCount,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
        LocalDateTime modifiedAt,
        String title,
        String content,
        Boolean isLiked,
        ArticleTag articleTag,
        Long commentCount,
        String ownerName,
        String ownerNickName,
        String address,
        String regionLv2,
        Long ownerMemberId,
        String ownerProfileImageUrl,
        List<String> imageUrls,
        String thumbNailImageUrl
) {
    public static GetArticleDetailResponse of(Article article,
                                              List<ArticleImage> images,
                                              Member owner,
                                              boolean isLiked,
                                              Long commentCount) {
        List<String> imageUrls = images.stream().map(ArticleImage::getImageUrl).toList();
        return GetArticleDetailResponse.builder()
                .articleId(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .likeCount(Optional.ofNullable(article.getLikeCount()).orElse(0))
                .createdAt(article.getCreatedAt())
                .modifiedAt(article.getModifiedAt())
                .ownerMemberId(owner.getId())
                .ownerName(owner.getRealName())
                .ownerNickName(owner.getNickName())
                .imageUrls(images.stream().map(ArticleImage::getImageUrl).toList())
                .thumbNailImageUrl(imageUrls.isEmpty() ? "" : imageUrls.get(0))
                .address(getAddress(article))
                .ownerProfileImageUrl(owner.getProfileImageUrl())
                .isLiked(isLiked)
                .articleTag(article.getArticleTag())
                .commentCount(commentCount)
                .regionLv2(Optional.ofNullable(article.getLv2()).orElse(""))
                .build();
    }
    private static String getAddress(Article article) {
        String address = article.getAddress();
        if (!address.isEmpty()) {
            String[] tokens = address.split(" ");
            int length = tokens.length;
            return length > 0 ? tokens[length - 1] : "";
        }
        return "";
    }
}
