package com.numberone.backend.domain.comment.repository.custom;

import com.numberone.backend.domain.article.entity.QArticle;
import com.numberone.backend.domain.comment.dto.response.GetCommentDto;
import com.numberone.backend.domain.comment.dto.response.QGetCommentDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.numberone.backend.domain.article.entity.QArticle.article;
import static com.numberone.backend.domain.comment.entity.QCommentEntity.commentEntity;

public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public CommentRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<GetCommentDto> findAllByArticle(Long articleId) {
        return queryFactory.select(new QGetCommentDto(commentEntity))
                .from(commentEntity)
                .leftJoin(commentEntity.parent)
                .fetchJoin()
                .where(commentEntity.article.id.eq(articleId))
                .orderBy(
                        commentEntity.parent.id.asc().nullsFirst(),
                        commentEntity.createdAt.asc()
                )
                .fetch();
    }


    @Override
    public Long countAllByArticle(Long articleId) {
        return queryFactory.select(commentEntity.count())
                .from(commentEntity)
                .innerJoin(article, article)
                .where(article.id.eq(articleId))
                .fetchOne();

    }
}
