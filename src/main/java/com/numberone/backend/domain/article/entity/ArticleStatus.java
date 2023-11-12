package com.numberone.backend.domain.article.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ArticleStatus {
    ACTIVATED,
    DELETED;
    private String value;
}
