package com.numberone.backend.domain.like.service;

import com.numberone.backend.domain.article.entity.Article;
import com.numberone.backend.domain.article.entity.ArticleTag;
import com.numberone.backend.domain.article.repository.ArticleRepository;
import com.numberone.backend.domain.comment.entity.CommentEntity;
import com.numberone.backend.domain.comment.repository.CommentRepository;
import com.numberone.backend.domain.like.entity.ArticleLike;
import com.numberone.backend.domain.like.entity.CommentLike;
import com.numberone.backend.domain.like.repository.ArticleLikeRepository;
import com.numberone.backend.domain.like.repository.CommentLikeRepository;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.notification.repository.NotificationRepository;
import com.numberone.backend.provider.fcm.service.FcmMessageProvider;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {
    @InjectMocks
    private LikeService likeService;
    @Mock
    private ArticleLikeRepository articleLikeRepository;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private FcmMessageProvider fcmMessageProvider;
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentLikeRepository commentLikeRepository;

    private Member loginMember;

    @BeforeEach
    void setUp() {
        setAuthentication();
    }

    @AfterEach
    void tearDown() {
        clearAuthentication();
    }

    @Test
    @DisplayName("게시글의 좋아요를 설정한다")
    void increaseArticleLike() {
        // given
        Member articleOwner = getDummyArticleOwner();
        given(articleOwner.getFcmToken())
                .willReturn("fcmToken123");
        given(memberRepository.findById(articleOwner.getId()))
                .willReturn(Optional.of(articleOwner));

        Article article = getDummyArticle(articleOwner);
        given(article.getId())
                .willReturn(1L);
        given(articleRepository.findById(article.getId()))
                .willReturn(Optional.of(article));

        //when
        likeService.increaseArticleLike(article.getId());

        // then
        assertThat(article.getLikeCount()).isEqualTo(1);
        verify(articleLikeRepository, times(1)).save(any());
        verify(fcmMessageProvider, times(1)).sendFcm(eq(articleOwner.getFcmToken()), any(), any());
        verify(notificationRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("게시글의 좋아요를 취소한다")
    void decreaseArticleLike() {
        // given
        Member articleOwner = getDummyArticleOwner();

        Article article = getDummyArticle(articleOwner);
        article.increaseLikeCount();
        article.increaseLikeCount();
        given(article.getId())
                .willReturn(1L);
        given(articleRepository.findById(article.getId()))
                .willReturn(Optional.of(article));

        ArticleLike articleLike = new ArticleLike(loginMember, article);
        given(articleLikeRepository.existsByMemberIdAndArticleId(loginMember.getId(), article.getId()))
                .willReturn(true);
        given(articleLikeRepository.findByMemberIdAndArticleId(loginMember.getId(), article.getId()))
                .willReturn(Optional.of(articleLike));

        //when
        likeService.decreaseArticleLike(article.getId());

        // then
        assertThat(article.getLikeCount()).isEqualTo(1);
        verify(articleLikeRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("댓글의 좋아요를 설정한다")
    void increaseCommentLike() {
        //given
        Member articleOwner = getDummyArticleOwner();

        Article article = getDummyArticle(articleOwner);

        Member commentOwner = getDummyCommentOwner();
        given(memberRepository.findById(commentOwner.getId()))
                .willReturn(Optional.of(commentOwner));
        given(commentOwner.getFcmToken())
                .willReturn("fcmToken123");

        CommentEntity commentEntity = getDummyComment(article, commentOwner);
        given(commentEntity.getId())
                .willReturn(1L);
        given(commentRepository.findById(commentEntity.getId()))
                .willReturn(Optional.of(commentEntity));

        //when
        likeService.increaseCommentLike(commentEntity.getId());

        //then
        assertThat(commentEntity.getLikeCount()).isEqualTo(1);
        verify(commentLikeRepository, times(1)).save(any());
        verify(fcmMessageProvider, times(1)).sendFcm(eq(commentOwner.getFcmToken()), any(), any());
        verify(notificationRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("댓글의 좋아요를 취소한다")
    void decreaseCommentLike() {
        //given
        Member articleOwner = getDummyArticleOwner();

        Article article = getDummyArticle(articleOwner);

        Member commentOwner = getDummyCommentOwner();

        CommentEntity commentEntity = getDummyComment(article, commentOwner);
        commentEntity.increaseLikeCount();
        commentEntity.increaseLikeCount();
        given(commentEntity.getId())
                .willReturn(1L);
        given(commentRepository.findById(commentEntity.getId()))
                .willReturn(Optional.of(commentEntity));

        CommentLike commentLike = new CommentLike(loginMember, commentEntity);
        given(commentLikeRepository.existsByMemberIdAndCommentId(loginMember.getId(), commentEntity.getId()))
                .willReturn(true);
        given(commentLikeRepository.findByMemberIdAndCommentId(loginMember.getId(), commentEntity.getId()))
                .willReturn(Optional.of(commentLike));

        //when
        likeService.decreaseCommentLike(commentEntity.getId());

        //then
        assertThat(commentEntity.getLikeCount()).isEqualTo(1);
        verify(commentLikeRepository, times(1)).delete(any());
    }

    private Member getDummyCommentOwner() {
        Member commentOwner = spy(Member.ofKakao(3456L));
        given(commentOwner.getId())
                .willReturn(3L);
        return commentOwner;
    }

    private Member getDummyArticleOwner() {
        Member articleOwner = spy(Member.ofKakao(2345L));
        given(articleOwner.getId())
                .willReturn(2L);
        return articleOwner;
    }

    private Article getDummyArticle(Member articleOwner) {
        return spy(Article.of("title1", "content1", articleOwner.getId(), ArticleTag.LIFE));
    }

    private CommentEntity getDummyComment(Article article, Member commentOwner) {
        return spy(new CommentEntity("hello", article, commentOwner));
    }

    private void setAuthentication() {
        loginMember = spy(Member.ofKakao(1234L));
        given(loginMember.getId())
                .willReturn(1L);
        given(memberRepository.findById(loginMember.getId()))
                .willReturn(Optional.of(loginMember));

        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(loginMember.getId(), null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void clearAuthentication() {
        SecurityContextHolder.clearContext();
    }
}