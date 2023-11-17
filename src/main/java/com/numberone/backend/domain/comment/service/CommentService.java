package com.numberone.backend.domain.comment.service;

import com.numberone.backend.domain.article.entity.Article;
import com.numberone.backend.domain.article.repository.ArticleRepository;
import com.numberone.backend.domain.articleparticipant.entity.ArticleParticipant;
import com.numberone.backend.domain.articleparticipant.repository.ArticleParticipantRepository;
import com.numberone.backend.domain.comment.dto.request.CreateChildCommentRequest;
import com.numberone.backend.domain.comment.dto.response.CreateChildCommentResponse;
import com.numberone.backend.domain.comment.dto.response.DeleteCommentResponse;
import com.numberone.backend.domain.comment.dto.response.GetCommentDto;
import com.numberone.backend.domain.comment.entity.CommentEntity;
import com.numberone.backend.domain.comment.repository.CommentRepository;
import com.numberone.backend.domain.like.entity.CommentLike;
import com.numberone.backend.domain.like.repository.CommentLikeRepository;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.token.util.SecurityContextProvider;
import com.numberone.backend.exception.notfound.NotFoundArticleException;
import com.numberone.backend.exception.notfound.NotFoundCommentException;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
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

    @Transactional
    public CreateChildCommentResponse createChildComment(
            Long articleId,
            Long parentCommentId,
            CreateChildCommentRequest request) {

        String principal = SecurityContextProvider.getAuthenticatedUserEmail();
        Member member = memberRepository.findByEmail(principal)
                .orElseThrow(NotFoundMemberException::new);
        Article article = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
        CommentEntity parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(NotFoundCommentException::new);

        CommentEntity childComment = commentRepository.save(new CommentEntity(request.getContent(), article, member));
        childComment.updateParent(parentComment);

        articleParticipantRepository.save(new ArticleParticipant(article, member));

        return CreateChildCommentResponse.of(childComment);
    }

    public List<GetCommentDto> getCommentsByArticle(Long articleId) {
        String principal = SecurityContextProvider.getAuthenticatedUserEmail();
        Member member = memberRepository.findByEmail(principal)
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
