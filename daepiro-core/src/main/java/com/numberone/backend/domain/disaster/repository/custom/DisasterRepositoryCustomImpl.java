package com.numberone.backend.domain.disaster.repository.custom;

import com.numberone.backend.domain.disaster.entity.Disaster;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.numberone.backend.domain.disaster.entity.QDisaster.disaster;

@RequiredArgsConstructor
public class DisasterRepositoryCustomImpl implements DisasterRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Disaster> findDisastersInAddressAfterTime(String address, LocalDateTime time) {
        return queryFactory
                .select(disaster)
                .from(disaster)
                .where(
                        Expressions.stringTemplate("{0}", Expressions.constant(address)).startsWith(disaster.location)
                                .and(disaster.generatedAt.after(time))
                )
                .orderBy(disaster.generatedAt.desc())
                .fetch();
    }
}
