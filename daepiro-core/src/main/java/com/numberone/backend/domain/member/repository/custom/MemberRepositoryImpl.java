package com.numberone.backend.domain.member.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.numberone.backend.domain.member.entity.QMember.member;

public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Long> findAllByLocation(String targetLocation) {
        return queryFactory.select(member.id)
                .from(member)
                .where(member.location.contains(targetLocation))
                .fetch();
    }


}
