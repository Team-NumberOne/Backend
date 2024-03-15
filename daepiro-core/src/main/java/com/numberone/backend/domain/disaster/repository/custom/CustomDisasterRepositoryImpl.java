package com.numberone.backend.domain.disaster.repository.custom;

import com.numberone.backend.domain.disaster.entity.Disaster;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.numberone.backend.domain.disaster.entity.QDisaster.disaster;

@RequiredArgsConstructor
public class CustomDisasterRepositoryImpl implements CustomDisasterRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Disaster> findDisastersInAddressAfterTime(String address, LocalDateTime time) {
        return queryFactory
                .select(disaster)
                .from(disaster)
                .where(disaster.location.like(address)
                        .and(disaster.generatedAt.after(time)))
                .orderBy(disaster.generatedAt.desc())
                .fetch();
    }
}
