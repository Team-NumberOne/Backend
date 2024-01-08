package com.numberone.backend.domain.article.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numberone.backend.domain.article.entity.Article;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DeleteArticleResponse {

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
    private LocalDateTime deletedAt;

    public static DeleteArticleResponse of(Article article){
        return DeleteArticleResponse.builder()
                .id(article.getId())
                .deletedAt(article.getModifiedAt())
                .build();
    }
}
