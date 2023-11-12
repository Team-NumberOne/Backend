package com.numberone.backend.domain.article.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ArticleTag {
    LIFE, // 일상
    TRAFFIC, // 교통
    SAFETY, // 치안
    NONE; // 기타
    private String value;
}
