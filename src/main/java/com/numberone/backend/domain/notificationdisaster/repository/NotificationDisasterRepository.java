package com.numberone.backend.domain.notificationdisaster.repository;

import com.numberone.backend.domain.notificationdisaster.entity.NotificationDisaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationDisasterRepository extends JpaRepository<NotificationDisaster, Long> {
    void deleteAllByMemberId(Long memberId);
}
