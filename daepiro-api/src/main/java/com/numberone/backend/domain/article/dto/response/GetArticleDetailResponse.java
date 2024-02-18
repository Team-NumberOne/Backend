package com.numberone.backend.domain.article.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numberone.backend.domain.article.entity.Article;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
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

        String address = "";

        String articleAddress = article.getAddress();
        if(!articleAddress.isEmpty()){
            String[] elements = articleAddress.split(" ");
            switch (elements.length){
                case 3 -> address = elements[2];
                case 2 -> address = elements[1];
                case 1 -> address = elements[0];
                default ->address = "";
            }
        } else {
            address = "";
        }


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
                .address(address)
                .ownerProfileImageUrl(owner.getProfileImageUrl())
                .isLiked(memberLikedArticleList.contains(article.getId()))
                .articleTag(article.getArticleTag())
                .commentCount(commentCount)
                .regionLv2(Optional.ofNullable(article.getLv2())
                        .orElse(""))
                .build();
    }

}
