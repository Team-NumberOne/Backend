package com.numberone.backend.domain.article.dto.response;

import com.numberone.backend.domain.article.entity.Article;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UploadArticleResponse {

    private Long articleId;
    private LocalDateTime createdAt;

    // 이미지 관련
    private List<String> imageUrls;
    private String thumbNailImageUrl;

    public static UploadArticleResponse of(Article article, List<String> imageUrls, String thumbNailImageUrl){
        return UploadArticleResponse.builder()
                .articleId(article.getId())
                .createdAt(article.getCreatedAt())
                .imageUrls(imageUrls)
                .thumbNailImageUrl(thumbNailImageUrl)
                .build();
    }

}
