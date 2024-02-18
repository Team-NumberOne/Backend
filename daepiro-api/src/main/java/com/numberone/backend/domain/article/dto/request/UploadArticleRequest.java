package com.numberone.backend.domain.article.dto.request;

import com.numberone.backend.domain.article.entity.ArticleTag;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UploadArticleRequest {

    // 글 관련
    @NotNull(message = "글 제목은 null 일 수 없습니다.")
    private String title; // 제목

    @NotNull(message = "내용은 null 일 수 없습니다.")
    private String content; // 내용

    @NotNull(message = """
            게시글의 태그를 하나 선택해주세요.
            """)
    private ArticleTag articleTag; // 게시글 태그

    // 이미지 관련
    private List<MultipartFile> imageList; // 이미지 리스트

    private Double longitude;
    private Double latitude;

    @NotNull(message = "동 위치 정보 제공 동의는 null 일 수 없습니다.")
    private boolean regionAgreementCheck; // 동 정보 제공 동의

}
