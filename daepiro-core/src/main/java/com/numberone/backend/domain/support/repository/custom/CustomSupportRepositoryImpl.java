package com.numberone.backend.domain.support.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.numberone.backend.domain.support.entity.QSupport.support;

public class CustomSupportRepositoryImpl implements CustomSupportRepository{
    private final JPAQueryFactory queryFactory;

    public CustomSupportRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Long getSupportCnt() {
        return queryFactory
                .select(support.id.countDistinct())
                .from(support)
                .fetchOne();
    }
}
