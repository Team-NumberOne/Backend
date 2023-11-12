package com.numberone.backend.domain.article.service;

import com.numberone.backend.domain.article.dto.request.UploadArticleRequest;
import com.numberone.backend.domain.article.dto.response.UploadArticleResponse;
import com.numberone.backend.domain.article.entity.Article;
import com.numberone.backend.domain.article.repository.ArticleRepository;
import com.numberone.backend.domain.articleimage.entity.ArticleImage;
import com.numberone.backend.domain.articleimage.repository.ArticleImageRepository;
import com.numberone.backend.domain.articleparticipant.entity.ArticleParticipant;
import com.numberone.backend.domain.articleparticipant.repository.ArticleParticipantRepository;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.token.util.SecurityContextProvider;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
import com.numberone.backend.support.s3.S3Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final ArticleParticipantRepository articleParticipantRepository;
    private final ArticleImageRepository articleImageRepository;
    private final S3Provider s3Provider;

    @Transactional
    public UploadArticleResponse uploadArticle(UploadArticleRequest request) {
        String principal = SecurityContextProvider.getAuthenticatedUserEmail();
        Member owner = memberRepository.findByEmail(principal)
                .orElseThrow(NotFoundMemberException::new);

        // 1. 게시글 생성 ( 제목, 내용, 작성자 아이디, 태그)
        Article article = articleRepository.save(
                new Article(
                        request.getTitle(),
                        request.getContent(),
                        owner.getId(),
                        request.getArticleTag())
        );
        articleParticipantRepository.save(
                new ArticleParticipant(article, owner.getId())
        );

        // 2. 이미지 업로드
        List<ArticleImage> articleImages = new ArrayList<>();
        List<String> imageUrls = new ArrayList<>();
        String thumbNailImageUrl = "";
        Long thumbNailImageId = 1L;
        if (!Objects.isNull(request.getImageList())) {
            List<MultipartFile> imageList = request.getImageList();

            for (int i = 0; i < imageList.size(); i++) {
                String imageUrl = s3Provider.uploadImage(imageList.get(i));
                imageUrls.add(imageUrl);

                ArticleImage savedArticleImage = articleImageRepository.save(
                        new ArticleImage(article, imageUrl)
                );
                articleImages.add(savedArticleImage);
                if (Objects.equals(i, request.getThumbNailImageIdx())) {
                    thumbNailImageUrl = imageUrl;
                    thumbNailImageId = savedArticleImage.getId();
                }

            }
        }

        // 3. 게시글 - 이미지 연관 관계 설정
        article.updateArticleImage(articleImages, thumbNailImageId);

        return UploadArticleResponse.of(article, imageUrls, thumbNailImageUrl);
    }

}
