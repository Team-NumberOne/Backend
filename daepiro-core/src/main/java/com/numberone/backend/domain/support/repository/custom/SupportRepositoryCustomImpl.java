package com.numberone.backend.domain.support.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.numberone.backend.domain.support.entity.QSupport.support;

@RequiredArgsConstructor
public class SupportRepositoryCustomImpl implements SupportRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Long getSupportCnt() {
        return queryFactory
                .select(support.member.countDistinct())
                .from(support)
                .fetchOne();
    }
}
