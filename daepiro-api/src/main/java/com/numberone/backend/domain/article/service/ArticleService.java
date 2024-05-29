package com.numberone.backend.domain.article.service;

import com.numberone.backend.domain.article.dto.parameter.ArticleSearchParameter;
import com.numberone.backend.domain.article.dto.request.ModifyArticleRequest;
import com.numberone.backend.domain.article.dto.request.UploadArticleRequest;
import com.numberone.backend.domain.article.dto.response.*;
import com.numberone.backend.domain.article.entity.Article;
import com.numberone.backend.domain.article.entity.ArticleStatus;
import com.numberone.backend.domain.article.repository.ArticleRepository;
import com.numberone.backend.domain.articleimage.entity.ArticleImage;
import com.numberone.backend.domain.articleimage.repository.ArticleImageRepository;
import com.numberone.backend.domain.comment.dto.request.CreateCommentRequest;
import com.numberone.backend.domain.comment.dto.response.CreateCommentResponse;
import com.numberone.backend.domain.comment.entity.CommentEntity;
import com.numberone.backend.domain.comment.repository.CommentRepository;
import com.numberone.backend.domain.like.entity.ArticleLike;
import com.numberone.backend.domain.like.repository.ArticleLikeRepository;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.notification.entity.NotificationEntity;
import com.numberone.backend.domain.notification.entity.NotificationTag;
import com.numberone.backend.domain.notification.repository.NotificationRepository;
import com.numberone.backend.domain.notificationregion.entity.NotificationRegion;
import com.numberone.backend.exception.conflict.UnauthorizedLocationException;
import com.numberone.backend.exception.notfound.NotFoundArticleException;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
import com.numberone.backend.provider.fcm.service.FcmMessageProvider;
import com.numberone.backend.provider.location.LocationProvider;
import com.numberone.backend.provider.s3.S3Provider;
import com.numberone.backend.provider.security.SecurityContextProvider;
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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {

    // todo: 리팩토링 (db 정리하고, 불필요한 쿼리 + 비효율적인 코드 모두 제거 )

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final ArticleImageRepository articleImageRepository;
    private final CommentRepository commentRepository;
    private final ArticleLikeRepository articleLikeRepository;
    private final NotificationRepository notificationRepository;

    private final S3Provider s3Provider;
    private final LocationProvider locationProvider;
    private final FcmMessageProvider fcmMessageProvider;

    @Transactional
    public UploadArticleResponse uploadArticle(UploadArticleRequest request, Long userId) {
        Member owner = memberRepository.findById(userId)
                .orElseThrow(NotFoundMemberException::new);
        Article article = articleRepository.save(Article.of(request.title(), request.content(), owner, request.articleTag()));

        if (request.regionAgreementCheck() && request.isValidPosition()) {
            updateArticleAddress(request, article, owner);
        }

        if (request.hasImage()) {
            List<ArticleImage> articleImages = uploadImages(article, request.imageList());
            article.updateThumbNailImageUrlId(articleImages.get(0).getId());
            return UploadArticleResponse.ofImages(article, articleImages);
        }

        return UploadArticleResponse.from(article);
    }

    @Transactional
    public DeleteArticleResponse deleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
        article.updateArticleStatus(ArticleStatus.DELETED);
        return DeleteArticleResponse.of(article);
    }

    public GetArticleDetailResponse getArticleDetail(Long articleId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        Article article = articleRepository.findByIdFetchJoin(articleId)
                .orElseThrow(NotFoundArticleException::new);

        Long commentCount = commentRepository.countAllByArticle(articleId);
        boolean isLiked = getIsLikedArticle(member, articleId);

        return GetArticleDetailResponse.of(
                article,
                article.getArticleImages(),
                article.getArticleOwner(),
                isLiked,
                commentCount
        );
    }

    public Slice<GetArticleListResponse> getArticleList(Long memberId, ArticleSearchParameter param, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        Slice<GetArticleListResponse> slice = articleRepository.findAllNoOffset(param, pageable);
        List<GetArticleListResponse> content = slice.getContent();

        updateArticleCommentCount(content);
        updateArticleLiked(member, content);

        return new SliceImpl<>(content, pageable, slice.hasNext());
    }

    @Transactional
    public CreateCommentResponse createComment(Long articleId, CreateCommentRequest request) {
        long id = SecurityContextProvider.getAuthenticatedUserId();
        Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundMemberException::new);
        Article article = articleRepository.findByIdFetchJoin(articleId)
                .orElseThrow(NotFoundArticleException::new);
        CommentEntity savedComment = commentRepository.save(
                new CommentEntity(request.getContent(), article, member)
        );
        Member articleOwner = article.getArticleOwner();

        String memberName = member.getNickName() != null ? member.getNickName() : member.getRealName();
        String title = String.format("""
                나의 게시글에 %s님이 댓글을 달았어요.""", memberName);
        String body = "대피로에 접속하여 확인하세요!";

        fcmMessageProvider.sendFcm(articleOwner.getFcmToken(), title, body);
        notificationRepository.save(
                new NotificationEntity(articleOwner, NotificationTag.COMMUNITY, title, body, true)
        );

        return CreateCommentResponse.of(savedComment);
    }

    @Transactional
    public ModifyArticleResponse modifyArticle(Long articleId, ModifyArticleRequest request) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
        article.modify(request.title(), request.content(), request.articleTag());

        if (request.hasImage()) {
            List<ArticleImage> articleImages = uploadImages(article, request.imageList());
            article.updateThumbNailImageUrlId(articleImages.get(0).getId());
            return ModifyArticleResponse.ofImages(article, articleImages);
        }

        return ModifyArticleResponse.from(article);
    }

    private void updateArticleLiked(Member member, List<GetArticleListResponse> content) {
        Set<Long> likedArticleIds = articleLikeRepository.findByMember(member)
                .stream().map(ArticleLike::getArticleId)
                .collect(Collectors.toSet());
        content.forEach(e -> {
            if (likedArticleIds.contains(e.getId())) {
                e.setLiked(true);
            }
        });
    }

    private void updateArticleCommentCount(List<GetArticleListResponse> content) {
        Set<Long> articleIds = content.stream().map(GetArticleListResponse::getId).collect(Collectors.toSet());
        Map<Long, Long> commentCountByArticleId = articleRepository.findCommentCountByArticleIdIn(articleIds);
        content.forEach(e -> {
            Long articleId = e.getId();
            if (commentCountByArticleId.containsKey(articleId)) {
                e.setCommentCount(commentCountByArticleId.get(articleId));
            }
        });
    }

    private void updateArticleAddress(UploadArticleRequest request, Article article, Member owner) {
        String address = locationProvider.pos2address(request.latitude(), request.longitude());
        article.updateAddress(address);
        validateLocation(owner, address);
    }

    private List<ArticleImage> uploadImages(Article article, List<MultipartFile> images) {
        List<ArticleImage> articleImages = new ArrayList<>();
        for (MultipartFile image : images) {
            String url = s3Provider.uploadImage(image);
            articleImages.add(new ArticleImage(article, url));
        }
        return articleImageRepository.saveAll(articleImages);
    }

    private void validateLocation(Member member, String realLocation) {
        List<String> regionLv2List = member.getNotificationRegions()
                .stream().map(NotificationRegion::getLv2).toList();
        String[] realRegions = realLocation.split(" ");

        if (realRegions.length >= 1 && !regionLv2List.contains(realRegions[1])) {
            throw new UnauthorizedLocationException();
        }
    }

    private boolean getIsLikedArticle(Member member, Long articleId) {
        Set<Long> likedArticleIds = member.getArticleLikes()
                .stream()
                .map(ArticleLike::getArticleId)
                .collect(Collectors.toSet());
        return likedArticleIds.contains(articleId);
    }

}
