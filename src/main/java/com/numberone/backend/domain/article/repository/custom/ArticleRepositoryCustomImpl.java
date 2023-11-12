package com.numberone.backend.domain.article.repository.custom;

import com.numberone.backend.domain.article.dto.response.ArticleSearchParameter;
import com.numberone.backend.domain.article.dto.response.GetArticleListResponse;
import com.numberone.backend.domain.article.dto.response.QGetArticleListResponse;
import com.numberone.backend.domain.article.entity.ArticleTag;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;
import java.util.Objects;

import static com.numberone.backend.domain.article.entity.QArticle.article;

public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ArticleRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Slice<GetArticleListResponse> getArticlesNoOffSetPaging(ArticleSearchParameter param, Pageable pageable) {
        List<GetArticleListResponse> result = queryFactory.select(new QGetArticleListResponse(
                        article,
                        article.articleOwnerId,
                        article.thumbNailImageUrlId
                ))
                .from(article)
                .where(
                        ltArticleId(param.getLastArticleId()),
                        checkTagCondition(param.getTag())
                )
                .orderBy(article.id.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();
        return checkLastPage(pageable, result);
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

    private Slice<GetArticleListResponse> checkLastPage(Pageable pageable, List<GetArticleListResponse> result) {
        boolean hasNext = false;

        if (result.size() > pageable.getPageSize()) {
            hasNext = true;
            result.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(result, pageable, hasNext);
    }

}
