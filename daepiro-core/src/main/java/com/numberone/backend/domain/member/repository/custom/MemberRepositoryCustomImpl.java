package com.numberone.backend.domain.member.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.numberone.backend.domain.member.entity.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> findAllByLocation(String targetLocation) {
        return queryFactory.select(member.id)
                .from(member)
                .where(member.location.contains(targetLocation))
                .fetch();
    }


}
