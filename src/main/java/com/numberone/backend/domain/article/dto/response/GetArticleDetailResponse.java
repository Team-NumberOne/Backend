package com.numberone.backend.domain.article.dto.response;

import com.numberone.backend.domain.article.entity.Article;
import com.numberone.backend.domain.article.entity.ArticleStatus;
import com.numberone.backend.domain.article.entity.ArticleTag;
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
    private boolean isLiked;
    private ArticleTag articleTag;
    private Long commentCount;

    // 작성자 관련
    private String ownerName;
    private String ownerNickName;
    private String address;
    private String regionLv2;
    private Long ownerMemberId;
    private String ownerProfileImageUrl;

    // 이미지 관련
    private List<String> imageUrls;
    private String thumbNailImageUrl;

    public static GetArticleDetailResponse of(
            Article article,
            List<String> imageUrls,
            String thumbNailImageUrl,
            Member owner,
            List<Long> memberLikedArticleList,
            Long commentCount ) {
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
                .ownerMemberId(owner.getId())
                .ownerName(owner.getRealName())
                .ownerNickName(owner.getNickName())
                .imageUrls(imageUrls)
                .thumbNailImageUrl(thumbNailImageUrl)
                .address(article.getAddress())
                .ownerProfileImageUrl(owner.getProfileImageUrl())
                .isLiked(memberLikedArticleList.contains(article.getId()))
                .articleTag(article.getArticleTag())
                .commentCount(commentCount)
                .regionLv2(Optional.ofNullable(article.getLv2())
                        .orElse(""))
                .build();
    }

}
