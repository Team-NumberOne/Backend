package com.numberone.backend.domain.article.repository.custom;

import com.numberone.backend.domain.article.dto.parameter.ArticleSearchParameter;
import com.numberone.backend.domain.article.dto.response.GetArticleListResponse;
import com.numberone.backend.domain.article.dto.response.QGetArticleListResponse;
import com.numberone.backend.domain.article.entity.ArticleStatus;
import com.numberone.backend.domain.article.entity.ArticleTag;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.*;

import static com.numberone.backend.domain.article.entity.QArticle.article;
import static com.numberone.backend.domain.articleimage.entity.QArticleImage.articleImage;
import static com.numberone.backend.domain.comment.entity.QCommentEntity.commentEntity;
import static com.numberone.backend.domain.member.entity.QMember.member;

@RequiredArgsConstructor
public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<GetArticleListResponse> findAllNoOffset(ArticleSearchParameter param, Pageable pageable) {
        List<GetArticleListResponse> content = queryFactory.select(new QGetArticleListResponse(
                        article.id,
                        article.articleTag,
                        article.title,
                        article.content,
                        article.address,
                        article.articleStatus,
                        article.likeCount,
                        member.nickName,
                        member.id,
                        article.createdAt,
                        articleImage.imageUrl,
                        articleImage.id
                ))
                .from(article)
                .leftJoin(member).on(article.articleOwner.id.eq(member.id))
                .leftJoin(articleImage).on(article.thumbNailImageUrlId.eq(articleImage.id))
                .where(
                        ltArticleId(param.getLastArticleId()),
                        checkTagCondition(param.getTag()),
                        article.articleStatus.eq(ArticleStatus.ACTIVATED),
                        article.lv2.eq(param.getRegionLv2())
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(article.id.desc())
                .fetch();

        return new SliceImpl<>(content, pageable, checkLastPage(pageable, content));
    }

    @Override
    public Map<Long, Long> findCommentCountByArticleIdIn(Set<Long> articleIds) {
        List<Tuple> tuples = queryFactory.select(article.id, commentEntity.count())
                .from(article)
                .leftJoin(article.comments, commentEntity)
                .fetch();

        HashMap<Long, Long> result = new HashMap<>();
        for (Tuple tuple : tuples) {
            result.put(tuple.get(article.id), tuple.get(commentEntity.count()));
        }
        return result;
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
