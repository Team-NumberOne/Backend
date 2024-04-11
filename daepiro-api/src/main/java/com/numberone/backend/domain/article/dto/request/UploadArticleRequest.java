package com.numberone.backend.domain.article.dto.request;

import com.numberone.backend.domain.article.entity.ArticleTag;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record UploadArticleRequest(
        @NotNull(message = "글 제목은 null 일 수 없습니다.")
        String title,
        @NotNull(message = "내용은 null 일 수 없습니다.")
        String content,
        @NotNull(message = "게시글 태그는 null 일 수 없습니다.")
        ArticleTag articleTag,
        List<MultipartFile> imageList,
        Double longitude,
        Double latitude,
        @NotNull(message = "위치 정보 제공 동의는 null 일 수 없습니다.")
        Boolean regionAgreementCheck
) {
        public boolean isValidPosition(){
                return (longitude != null) && (latitude != null);
        }
        public boolean hasImage() {
                return imageList != null && !imageList.isEmpty();
        }
}
