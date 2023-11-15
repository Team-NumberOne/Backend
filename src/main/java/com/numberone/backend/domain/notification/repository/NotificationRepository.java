package com.numberone.backend.domain.notification.repository;

import com.numberone.backend.domain.notification.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
