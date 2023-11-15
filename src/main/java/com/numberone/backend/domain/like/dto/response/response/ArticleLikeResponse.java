package com.numberone.backend.domain.like.dto.response.response;

import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ArticleLikeResponse {
    private Long articleId;
    private Integer currentLikeCount;
}
