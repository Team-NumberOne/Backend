package com.numberone.backend.domain.comment.service;

import com.numberone.backend.domain.article.entity.Article;
import com.numberone.backend.domain.article.repository.ArticleRepository;
import com.numberone.backend.domain.articleparticipant.entity.ArticleParticipant;
import com.numberone.backend.domain.articleparticipant.repository.ArticleParticipantRepository;
import com.numberone.backend.domain.comment.dto.request.CreateChildCommentRequest;
import com.numberone.backend.domain.comment.dto.response.CreateChildCommentResponse;
import com.numberone.backend.domain.comment.dto.response.DeleteCommentResponse;
import com.numberone.backend.domain.comment.dto.GetCommentDto;
import com.numberone.backend.domain.comment.entity.CommentEntity;
import com.numberone.backend.domain.comment.repository.CommentRepository;
import com.numberone.backend.domain.like.entity.CommentLike;
import com.numberone.backend.domain.like.repository.CommentLikeRepository;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.notification.entity.NotificationEntity;
import com.numberone.backend.domain.notification.entity.NotificationTag;
import com.numberone.backend.domain.notification.repository.NotificationRepository;
import com.numberone.backend.provider.security.SecurityContextProvider;
import com.numberone.backend.exception.notfound.NotFoundArticleException;
import com.numberone.backend.exception.notfound.NotFoundCommentException;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
import com.numberone.backend.provider.fcm.service.FcmMessageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final ArticleParticipantRepository articleParticipantRepository;
    private final MemberRepository memberRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final NotificationRepository notificationRepository;
    private final FcmMessageProvider fcmMessageProvider;

    @Transactional
    public CreateChildCommentResponse createChildComment(
            Long articleId,
            Long parentCommentId,
            CreateChildCommentRequest request) {

        long principal = SecurityContextProvider.getAuthenticatedUserId();
        Member member = memberRepository.findById(principal)
                .orElseThrow(NotFoundMemberException::new);
        Article article = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
        CommentEntity parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(NotFoundCommentException::new);

        CommentEntity childComment = commentRepository.save(CommentEntity.of(request.getContent(), article, member));
        childComment.updateParent(parentComment);

        articleParticipantRepository.save(new ArticleParticipant(article, member));

        Member owner = memberRepository.findById(parentComment.getAuthorId())
                .orElseThrow(NotFoundMemberException::new);
        String memberName = member.getNickName() != null ? member.getNickName() : member.getRealName();

        String title = String.format("""
                나의 댓글에 %s님이 댓글을 달았어요.""", memberName);
        String body = "대피로에 접속하여 확인하세요!";
        fcmMessageProvider.sendFcm(owner.getFcmToken(), title, body);
        notificationRepository.save(
                new NotificationEntity(owner, NotificationTag.COMMUNITY, title, body, true)
        );

        return CreateChildCommentResponse.of(childComment);
    }

    public List<GetCommentDto> getCommentsByArticle(Long articleId) {
        long principal = SecurityContextProvider.getAuthenticatedUserId();
        Member member = memberRepository.findById(principal)
                .orElseThrow(NotFoundMemberException::new);
        Article article = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
        List<GetCommentDto> comments = commentRepository.findAllByArticle(article.getId());
        List<Long> likedCommentIdList = commentLikeRepository.findByMember(member)
                .stream().map(CommentLike::getCommentId)
                .toList();
        // 계층 구조로 변환 (추후 리팩토링 필요)
        List<GetCommentDto> result = new ArrayList<>();
        Map<Long, GetCommentDto> map = new HashMap<>();
        comments.forEach(
                comment -> {
                    CommentEntity commentEntity = commentRepository.findById(comment.getCommentId())
                            .orElseThrow(NotFoundCommentException::new);
                    Optional<Member> author = memberRepository.findById(commentEntity.getAuthorId());
                    comment.updateCommentInfo(author, likedCommentIdList);

                    map.put(comment.getCommentId(), comment);

                    if (comment.getParentCommentId() != null){
                        GetCommentDto parentComment = map.get(comment.getParentCommentId());
                        List<GetCommentDto> childComments = parentComment.getChildComments();
                        childComments.add(comment);
                    } else {
                        result.add(comment);
                    }
                }
        );

        return result;
    }

    @Transactional
    public DeleteCommentResponse deleteComment(Long commentId){
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(NotFoundCommentException::new);
        commentRepository.delete(commentEntity);
        return DeleteCommentResponse.builder()
                .commentId(commentId)
                .build();
    }

}
