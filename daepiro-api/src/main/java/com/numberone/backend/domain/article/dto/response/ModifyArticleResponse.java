package com.numberone.backend.domain.article.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numberone.backend.domain.article.entity.Article;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ModifyArticleResponse {

    private Long articleId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    // 이미지 관련
    private List<String> imageUrls;
    private String thumbNailImageUrl;

    // 작성자 주소
    private String address;

    public static ModifyArticleResponse of(Article article, List<String> imageUrls, String thumbNailImageUrl){
        return ModifyArticleResponse.builder()
                .articleId(article.getId())
                .createdAt(article.getCreatedAt())
                .modifiedAt(article.getModifiedAt())
                .imageUrls(imageUrls)
                .thumbNailImageUrl(thumbNailImageUrl)
                .address(article.getAddress())
                .build();
    }

}
