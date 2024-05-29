package com.numberone.backend.domain.article.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numberone.backend.domain.article.entity.Article;
import com.numberone.backend.domain.articleimage.entity.ArticleImage;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ModifyArticleResponse(
        Long articleId,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
        LocalDateTime modifiedAt,
        List<String> imageUrls,
        String thumbNailImageUrl,
        String address
) {
    public static ModifyArticleResponse from(Article article) {
        return ModifyArticleResponse.builder()
                .articleId(article.getId())
                .createdAt(article.getCreatedAt())
                .address(article.getAddress())
                .build();
    }

    public static ModifyArticleResponse ofImages(Article article, List<ArticleImage> images) {
        return ModifyArticleResponse.builder()
                .articleId(article.getId())
                .createdAt(article.getCreatedAt())
                .imageUrls(images.stream().map(ArticleImage::getImageUrl).toList())
                .thumbNailImageUrl(images.isEmpty() ? "" : images.get(0).getImageUrl())
                .address(article.getAddress())
                .build();
    }
}
