package com.numberone.backend.domain.notification.repository;

import com.numberone.backend.domain.notification.entity.NotificationEntity;
import com.numberone.backend.domain.notification.repository.custom.NotificationRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long>, NotificationRepositoryCustom {
}
