package com.numberone.backend.domain.support.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import static com.numberone.backend.domain.support.entity.QSupport.support;

@RequiredArgsConstructor
public class CustomSupportRepositoryImpl implements CustomSupportRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Long getSupportCnt() {
        return queryFactory
                .select(support.id.countDistinct())
                .from(support)
                .fetchOne();
    }
}
