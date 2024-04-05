package com.numberone.backend.domain.article.dto.response;

import com.numberone.backend.domain.article.entity.Article;
import com.numberone.backend.domain.articleimage.entity.ArticleImage;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record UploadArticleResponse(
        Long articleId,
        LocalDateTime createdAt,
        List<String> imageUrls,
        String thumbNailImageUrl,
        String address
) {
    public static UploadArticleResponse from (Article article){
        return UploadArticleResponse.builder()
                .articleId(article.getId())
                .createdAt(article.getCreatedAt())
                .address(article.getAddress())
                .build();
    }

    public static UploadArticleResponse ofImages (Article article, List<ArticleImage> images) {
        return UploadArticleResponse.builder()
                .articleId(article.getId())
                .createdAt(article.getCreatedAt())
                .imageUrls(images.stream().map(ArticleImage::getImageUrl).toList())
                .thumbNailImageUrl(images.isEmpty() ? "" : images.get(0).getImageUrl())
                .address(article.getAddress())
                .build();
    }
}
