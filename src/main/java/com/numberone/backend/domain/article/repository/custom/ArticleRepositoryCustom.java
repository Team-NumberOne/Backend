package com.numberone.backend.domain.article.repository.custom;

import com.numberone.backend.domain.article.dto.response.ArticleSearchParameter;
import com.numberone.backend.domain.article.dto.response.GetArticleListResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ArticleRepositoryCustom {
    Slice<GetArticleListResponse> getArticlesNoOffSetPaging(ArticleSearchParameter param, Pageable pageable);
}
