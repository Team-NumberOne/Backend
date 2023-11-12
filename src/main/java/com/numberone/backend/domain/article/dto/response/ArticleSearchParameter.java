package com.numberone.backend.domain.article.dto.response;

import com.numberone.backend.domain.article.entity.ArticleTag;
import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ArticleSearchParameter {
    private ArticleTag tag;
    private Long lastArticleId;
}
