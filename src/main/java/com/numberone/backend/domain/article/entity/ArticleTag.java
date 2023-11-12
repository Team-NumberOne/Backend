package com.numberone.backend.domain.article.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ArticleTag {
    LIFE, // 일상
    FRAUD, // 사기
    SAFETY, // 치안
    REPORT; // 제보
    private String value;
}
