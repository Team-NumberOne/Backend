package com.numberone.backend.domain.article.dto.request;

import com.numberone.backend.domain.article.entity.ArticleTag;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ModifyArticleRequest(
        @NotNull(message = "글 제목은 null 일 수 없습니다.")
        String title,
        @NotNull(message = "내용은 null 일 수 없습니다.")
        String content,
        @NotNull(message = "게시글의 태그를 하나 선택해주세요. LIFE(일상), FRAUD(사기), SAFETY(안전), REPORT(제보)")
        ArticleTag articleTag,
        List<MultipartFile> imageList,
        Long thumbNailImageIdx,
        Double longitude,
        Double latitude
) {
    public boolean hasImage() {
        return imageList != null && !imageList.isEmpty();
    }
}
