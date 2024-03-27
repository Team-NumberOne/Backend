package com.numberone.backend.domain.article.repository.custom;

import com.numberone.backend.domain.article.dto.parameter.ArticleSearchParameter;
import com.numberone.backend.domain.article.dto.response.GetArticleListResponse;
import com.numberone.backend.domain.article.dto.response.QGetArticleListResponse;
import com.numberone.backend.domain.article.entity.ArticleStatus;
import com.numberone.backend.domain.article.entity.ArticleTag;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;
import java.util.Objects;

import static com.numberone.backend.domain.article.entity.QArticle.article;

@RequiredArgsConstructor
public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<GetArticleListResponse> getArticlesNoOffSetPaging(ArticleSearchParameter param, Pageable pageable) {
        List<GetArticleListResponse> result = queryFactory.select(
                new QGetArticleListResponse(
                        article,
                        article.articleOwnerId,
                        article.thumbNailImageUrlId
                ))
                .from(article)
                .where(
                        ltArticleId(param.getLastArticleId()),
                        checkTagCondition(param.getTag()),
                        article.articleStatus.eq(ArticleStatus.ACTIVATED),
                        article.lv2.eq(param.getRegionLv2())
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(article.id.desc())
                .fetch();

        return new SliceImpl<>(result, pageable, checkLastPage(pageable, result));
    }

    private BooleanExpression checkTagCondition(ArticleTag tag) {
        if (Objects.isNull(tag)) {
            return null;
        }
        return article.articleTag.eq(tag);
    }

    private BooleanExpression ltArticleId(Long articleId) {
        if (Objects.isNull(articleId)) {
            return null;
        }
        return article.id.lt(articleId);
    }

    private boolean checkLastPage(Pageable pageable, List<GetArticleListResponse> result) {
        boolean hasNext = false;

        if (result.size() > pageable.getPageSize()) {
            hasNext = true;
            result.remove(pageable.getPageSize());
        }

        return hasNext;
    }

}
