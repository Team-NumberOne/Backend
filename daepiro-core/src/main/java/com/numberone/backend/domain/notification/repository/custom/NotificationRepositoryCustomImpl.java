package com.numberone.backend.domain.notification.repository.custom;

import com.numberone.backend.domain.notification.dto.parameter.NotificationSearchParameter;
import com.numberone.backend.domain.notification.dto.response.NotificationTabResponse;
import com.numberone.backend.domain.notification.dto.response.QNotificationTabResponse;
import com.numberone.backend.domain.notification.entity.NotificationTag;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;
import java.util.Objects;

import static com.numberone.backend.domain.member.entity.QMember.member;
import static com.numberone.backend.domain.notification.entity.QNotificationEntity.notificationEntity;

@RequiredArgsConstructor
public class NotificationRepositoryCustomImpl implements NotificationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<NotificationTabResponse> getNotificationTabPagesNoOffSetByMember(NotificationSearchParameter param, Long memberId, Pageable pageable) {
        List<NotificationTabResponse> result = queryFactory.select(new QNotificationTabResponse(notificationEntity))
                .from(notificationEntity)
                .innerJoin(member)
                .on(notificationEntity.receivedMemberId.eq(member.id))
                .where(
                        ltNotificationEntityId(param.getLastNotificationId()),
                        member.id.eq(memberId),
                        checkDisasterFlag(param.getIsDisaster())
                )
                .orderBy(notificationEntity.id.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();
        return new SliceImpl<>(result, pageable, checkLastPage(pageable, result));
    }

    private BooleanExpression checkDisasterFlag(boolean isDisaster) {
        if (!isDisaster) {
            return notificationEntity.notificationTag.ne(NotificationTag.DISASTER);
        }
        return notificationEntity.notificationTag.eq(NotificationTag.DISASTER);
    }

    private BooleanExpression ltNotificationEntityId(Long notificationId) {
        if (Objects.isNull(notificationId)) {
            return null;
        }
        return notificationEntity.id.lt(notificationId);
    }

    private boolean checkLastPage(Pageable pageable, List<NotificationTabResponse> result) {
        boolean hasNext = false;

        if (result.size() > pageable.getPageSize()) {
            hasNext = true;
            result.remove(pageable.getPageSize());
        }

        return hasNext;
    }
}
