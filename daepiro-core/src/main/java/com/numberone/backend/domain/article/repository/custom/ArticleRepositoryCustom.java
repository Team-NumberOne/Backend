package com.numberone.backend.domain.article.repository.custom;

import com.numberone.backend.domain.article.dto.parameter.ArticleSearchParameter;
import com.numberone.backend.domain.article.dto.response.GetArticleListResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Map;
import java.util.Set;

public interface ArticleRepositoryCustom {
    Slice<GetArticleListResponse> findAllNoOffset(ArticleSearchParameter param, Pageable pageable);

    Map<Long, Long> findCommentCountByArticleIdIn(Set<Long> articleIds);
}
