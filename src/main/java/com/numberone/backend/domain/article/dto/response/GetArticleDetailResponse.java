package com.numberone.backend.domain.article.dto.response;

import com.numberone.backend.domain.article.entity.Article;
import com.numberone.backend.domain.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetArticleDetailResponse {

    // 게시글 관련
    private Long articleId;
    private Integer likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String title;
    private String content;

    // 작성자 관련
    private String memberName;
    private String memberNickName;
    private String address; // todo: 더미 데이터
    private Long ownerMemberId;

    // 이미지 관련
    private List<String> imageUrls;
    private String thumbNailImageUrl;

    public static GetArticleDetailResponse of(Article article, List<String> imageUrls, String thumbNailImageUrl, Member member){
        return GetArticleDetailResponse.builder()
                .articleId(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .likeCount(
                        Optional.ofNullable(
                                article.getLikeCount()
                        ).orElse(0)
                )
                .createdAt(article.getCreatedAt())
                .modifiedAt(article.getModifiedAt())
                .ownerMemberId(member.getId())
                .memberName(member.getRealName())
                .memberNickName(member.getNickName())
                .imageUrls(imageUrls)
                .thumbNailImageUrl(thumbNailImageUrl)
                .address("서울시 광진구 자양동") // 교체
                .build();
    }

}
