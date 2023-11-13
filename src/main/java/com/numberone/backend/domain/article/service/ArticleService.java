package com.numberone.backend.domain.article.service;

import com.numberone.backend.domain.article.dto.request.UploadArticleRequest;
import com.numberone.backend.domain.article.dto.response.*;
import com.numberone.backend.domain.article.entity.Article;
import com.numberone.backend.domain.article.entity.ArticleStatus;
import com.numberone.backend.domain.article.repository.ArticleRepository;
import com.numberone.backend.domain.articleimage.entity.ArticleImage;
import com.numberone.backend.domain.articleimage.repository.ArticleImageRepository;
import com.numberone.backend.domain.articleparticipant.entity.ArticleParticipant;
import com.numberone.backend.domain.articleparticipant.repository.ArticleParticipantRepository;
import com.numberone.backend.domain.comment.dto.request.CreateCommentRequest;
import com.numberone.backend.domain.comment.dto.response.CreateCommentResponse;
import com.numberone.backend.domain.comment.entity.CommentEntity;
import com.numberone.backend.domain.comment.repository.CommentRepository;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.token.util.SecurityContextProvider;
import com.numberone.backend.exception.notfound.NotFoundArticleException;
import com.numberone.backend.exception.notfound.NotFoundArticleImageException;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
import com.numberone.backend.support.s3.S3Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final ArticleParticipantRepository articleParticipantRepository;
    private final ArticleImageRepository articleImageRepository;
    private final CommentRepository commentRepository;
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
                new ArticleParticipant(article, owner)
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


    @Transactional
    public DeleteArticleResponse deleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
        article.updateArticleStatus(ArticleStatus.DELETED);
        return DeleteArticleResponse.of(article);
    }

    public GetArticleDetailResponse getArticleDetail(Long articleId) {
        String principal = SecurityContextProvider.getAuthenticatedUserEmail();
        Member owner = memberRepository.findByEmail(principal)
                .orElseThrow(NotFoundMemberException::new);
        Article article = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);

        List<String> imageUrls = articleImageRepository.findByArticle(article)
                .stream()
                .map(ArticleImage::getImageUrl)
                .toList();


        Optional<ArticleImage> thumbNailImage = articleImageRepository.findById(article.getThumbNailImageUrlId());

        String thumbNailImageUrl = "";
        if (thumbNailImage.isPresent()) {
            thumbNailImageUrl = thumbNailImage.get().getImageUrl();
        }

        return GetArticleDetailResponse.of(article, imageUrls, thumbNailImageUrl, owner);
    }

    public Slice<GetArticleListResponse> getArticleListPaging(ArticleSearchParameter param, Pageable pageable) {
        return new SliceImpl<>(
                articleRepository.getArticlesNoOffSetPaging(param, pageable)
                        .stream()
                        .peek(this::updateArticleInfo)
                        .toList()
        );
    }

    public void updateArticleInfo(GetArticleListResponse articleInfo) {
        Long ownerId = articleInfo.getOwnerId();
        Long thumbNailImageUrlId = articleInfo.getThumbNailImageId();

        Optional<Member> owner = memberRepository.findById(ownerId);
        Optional<ArticleImage> articleImage = articleImageRepository.findById(thumbNailImageUrlId);

        articleInfo.updateInfo(owner, articleImage);
    }

    @Transactional
    public CreateCommentResponse createComment(Long articleId, CreateCommentRequest request){
        String principal = SecurityContextProvider.getAuthenticatedUserEmail();
        Member member = memberRepository.findByEmail(principal)
                .orElseThrow(NotFoundMemberException::new);
        Article article = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
        CommentEntity savedComment = commentRepository.save(
                new CommentEntity(request.getContent(), article)
        );

        articleParticipantRepository.save(new ArticleParticipant(article, member));

        return CreateCommentResponse.of(savedComment);
    }

}
