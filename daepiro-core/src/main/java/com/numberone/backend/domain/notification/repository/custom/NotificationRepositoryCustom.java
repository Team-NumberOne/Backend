package com.numberone.backend.domain.notification.repository.custom;

import com.numberone.backend.domain.notification.dto.parameter.NotificationSearchParameter;
import com.numberone.backend.domain.notification.dto.response.NotificationTabResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface NotificationRepositoryCustom {
    Slice<NotificationTabResponse> getNotificationTabPagesNoOffSetByMember(NotificationSearchParameter param, Long memberId, Pageable pageable);
}
