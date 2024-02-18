package com.numberone.backend.domain.article.dto.parameter;

import com.numberone.backend.domain.article.entity.ArticleTag;
import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ArticleSearchParameterDto {
    private ArticleTag tag;
    private Long lastArticleId;
    private Double longitude;
    private Double latitude;
    private String regionLv2; //  구

    public ArticleSearchParameter toParameter () {
        return ArticleSearchParameter.builder()
                .tag(tag)
                .lastArticleId(lastArticleId)
                .longitude(longitude)
                .latitude(latitude)
                .regionLv2(regionLv2)
                .build();
    }
}
