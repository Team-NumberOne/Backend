package com.numberone.backend.domain.notificationregion.repository;

import com.numberone.backend.domain.notificationregion.entity.NotificationRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRegionRepository extends JpaRepository<NotificationRegion, Long> {
    void deleteAllByMemberId(Long memberId);
}
