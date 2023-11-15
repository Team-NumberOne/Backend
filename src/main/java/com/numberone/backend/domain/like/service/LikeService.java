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
import com.numberone.backend.domain.token.util.SecurityContextProvider;
import com.numberone.backend.exception.conflict.AlreadyLikedException;
import com.numberone.backend.exception.conflict.AlreadyUnLikedException;
import com.numberone.backend.exception.notfound.NotFoundApiException;
import com.numberone.backend.exception.notfound.NotFoundCommentException;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
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


    @Transactional
    public Integer increaseArticleLike(Long articleId) {
        String principal = SecurityContextProvider.getAuthenticatedUserEmail();
        Member member = memberRepository.findByEmail(principal)
                .orElseThrow(NotFoundMemberException::new);
        Article article = articleRepository.findById(articleId)
                .orElseThrow(NotFoundApiException::new);
        if (isAlreadyLikedArticle(member, articleId)) {
            // 이미 좋아요를 누른 게시글입니다.
            throw new AlreadyLikedException();
        }
        article.increaseLikeCount();
        articleLikeRepository.save(new ArticleLike(member, article));

        return article.getLikeCount();
    }

    @Transactional
    public Integer decreaseArticleLike(Long articleId) {
        String principal = SecurityContextProvider.getAuthenticatedUserEmail();
        Member member = memberRepository.findByEmail(principal)
                .orElseThrow(NotFoundMemberException::new);
        Article article = articleRepository.findById(articleId)
                .orElseThrow(NotFoundApiException::new);
        if (!isAlreadyLikedArticle(member, articleId)) {
            // 좋아요를 누르지 않은 게시글이라 취소할 수 없습니다.
            throw new AlreadyUnLikedException();
        }
        article.decreaseLikeCount();

        // 사용자의 게시글 좋아요 목록에서 제거
        List<ArticleLike> articleLikeList = articleLikeRepository.findByMember(member);
        articleLikeList.removeIf(articleLike -> articleLike.getArticleId().equals(articleId));

        return article.getLikeCount();
    }

    @Transactional
    public Integer increaseCommentLike(Long commentId) {
        String principal = SecurityContextProvider.getAuthenticatedUserEmail();
        Member member = memberRepository.findByEmail(principal)
                .orElseThrow(NotFoundMemberException::new);
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(NotFoundCommentException::new);
        if (isAlreadyLikedComment(member, commentId)) {
            // 이미 좋아요를 누른 댓글입니다.
            throw new AlreadyLikedException();
        }
        commentEntity.increaseLikeCount();
        commentLikeRepository.save(new CommentLike(member, commentEntity));

        return commentEntity.getLikeCount();
    }

    @Transactional
    public Integer decreaseCommentLike(Long commentId) {
        String principal = SecurityContextProvider.getAuthenticatedUserEmail();
        Member member = memberRepository.findByEmail(principal)
                .orElseThrow(NotFoundMemberException::new);
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(NotFoundCommentException::new);
        if (!isAlreadyLikedComment(member, commentId)){
            // 좋아요를 누르지 않은 댓글이라 좋아요를 취소할 수 없습니다.
            throw new AlreadyUnLikedException();
        }
        commentEntity.decreaseLikeCount();
        // 사용자의 댓글 좋아요 목록에서 제거
        List<CommentLike> commentLikeList = commentLikeRepository.findByMember(member);
        commentLikeList.removeIf(commentLike -> commentLike.getCommentId().equals(commentId));

        return commentEntity.getLikeCount();
    }

    private boolean isAlreadyLikedArticle(Member member, Long articleId) {
        return articleLikeRepository.findByMember(member).stream()
                .anyMatch(articleLike -> articleLike.getArticleId().equals(articleId));
    }

    private boolean isAlreadyLikedComment(Member member, Long commentId) {
        return commentLikeRepository.findByMember(member).stream()
                .anyMatch(commentLike -> commentLike.getCommentId().equals(commentId));
    }

}
