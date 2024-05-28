package com.numberone.backend.domain.like.service;

import com.numberone.backend.domain.article.entity.Article;
import com.numberone.backend.domain.article.repository.ArticleRepository;
import com.numberone.backend.domain.comment.entity.CommentEntity;
import com.numberone.backend.domain.comment.repository.CommentRepository;
import com.numberone.backend.domain.like.entity.ArticleLike;
import com.numberone.backend.domain.like.entity.CommentLike;
import com.numberone.backend.domain.like.repository.ArticleLikeRepository;
import com.numberone.backend.domain.like.repository.CommentLikeRepository;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.notification.entity.NotificationEntity;
import com.numberone.backend.domain.notification.entity.NotificationTag;
import com.numberone.backend.domain.notification.repository.NotificationRepository;
import com.numberone.backend.exception.notfound.*;
import com.numberone.backend.provider.security.SecurityContextProvider;
import com.numberone.backend.exception.conflict.AlreadyLikedException;
import com.numberone.backend.exception.conflict.AlreadyUnLikedException;
import com.numberone.backend.provider.fcm.service.FcmMessageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final ArticleLikeRepository articleLikeRepository;
    private final ArticleRepository articleRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final FcmMessageProvider fcmMessageProvider;
    private final NotificationRepository notificationRepository;

    private final Integer BEST_ARTICLE_LIKE_COUNT = 20;

    @Transactional
    public Integer increaseArticleLike(Long articleId) {
        Long memberId = SecurityContextProvider.getAuthenticatedUserId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        Article article = articleRepository.findById(articleId)
                .orElseThrow(NotFoundApiException::new);

        if (isAlreadyLikedArticle(memberId, articleId))
            throw new AlreadyLikedException();
        article.increaseLikeCount();
        articleLikeRepository.save(new ArticleLike(member, article));

        Long ownerId = article.getArticleOwnerId();
        Member owner = memberRepository.findById(ownerId)
                .orElseThrow(NotFoundMemberException::new);

        String memberName = member.getNickName() != null ? member.getNickName() : member.getRealName();
        String title = String.format("""
                나의 게시글에 %s님이 좋아요를 눌렀어요.""", memberName);
        String body = "대피로에 접속하여 확인하세요!";
        fcmMessageProvider.sendFcm(owner.getFcmToken(), title, body);
        notificationRepository.save(
                new NotificationEntity(owner, NotificationTag.COMMUNITY, title, body, true)
        );

        return article.getLikeCount();
    }

    @Transactional
    public Integer decreaseArticleLike(Long articleId) {
        Long memberId = SecurityContextProvider.getAuthenticatedUserId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        Article article = articleRepository.findById(articleId)
                .orElseThrow(NotFoundApiException::new);
        if (!isAlreadyLikedArticle(member.getId(), articleId))
            throw new AlreadyUnLikedException();

        // 사용자의 게시글 좋아요 목록에서 제거
        article.decreaseLikeCount();
        ArticleLike articleLike = articleLikeRepository.findByMemberIdAndArticleId(memberId, articleId)
                .orElseThrow(NotFoundArticleLikeException::new);
        articleLikeRepository.delete(articleLike);

        return article.getLikeCount();
    }

    @Transactional
    public Integer increaseCommentLike(Long commentId) {
        Long memberId = SecurityContextProvider.getAuthenticatedUserId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(NotFoundCommentException::new);
        if (isAlreadyLikedComment(member.getId(), commentId))
            throw new AlreadyLikedException();

        commentEntity.increaseLikeCount();
        commentLikeRepository.save(new CommentLike(member, commentEntity));


        Long ownerId = commentEntity.getAuthorId();
        Member owner = memberRepository.findById(ownerId)
                .orElseThrow(NotFoundMemberException::new);

        String memberName = member.getNickName() != null ? member.getNickName() : member.getRealName();
        String title = String.format("""
                나의 댓글에 %s님이 좋아요를 눌렀어요.""", memberName);
        String body = "대피로에 접속하여 확인하세요!";
        fcmMessageProvider.sendFcm(owner.getFcmToken(), title, body);
        notificationRepository.save(
                new NotificationEntity(owner, NotificationTag.COMMUNITY, title, body, true)
        );

        return commentEntity.getLikeCount();
    }

    @Transactional
    public Integer decreaseCommentLike(Long commentId) {
        long memberId = SecurityContextProvider.getAuthenticatedUserId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(NotFoundCommentException::new);
        if (!isAlreadyLikedComment(member.getId(), commentId))
            throw new AlreadyUnLikedException();

        commentEntity.decreaseLikeCount();
        CommentLike commentLike = commentLikeRepository.findByMemberIdAndCommentId(memberId, commentId)
                .orElseThrow(NotFoundCommentLikeException::new);
        commentLikeRepository.delete(commentLike);

        return commentEntity.getLikeCount();
    }

    private boolean isAlreadyLikedArticle(Long memberId, Long articleId) {
        return articleLikeRepository.existsByMemberIdAndArticleId(memberId, articleId);
    }

    private boolean isAlreadyLikedComment(Long memberId, Long commentId) {
        return commentLikeRepository.existsByMemberIdAndCommentId(memberId, commentId);
    }
}
