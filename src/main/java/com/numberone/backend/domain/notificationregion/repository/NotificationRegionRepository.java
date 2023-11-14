package com.numberone.backend.domain.notificationregion.repository;

import com.numberone.backend.domain.notificationregion.entity.NotificationRegion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRegionRepository extends JpaRepository<NotificationRegion, Long> {
    void deleteAllByMemberId(Long memberId);
}
