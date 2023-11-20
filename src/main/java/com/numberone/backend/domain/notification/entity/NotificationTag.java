package com.numberone.backend.domain.notification.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationTag {
    COMMUNITY, // 커뮤니티
    SUPPORT, // 후원
    FAMILY, // 가족

    DISASTER, // 재난
    ;
    private String value;
}
