package com.numberone.backend.domain.comment.service;

import com.numberone.backend.domain.article.entity.Article;
import com.numberone.backend.domain.article.repository.ArticleRepository;
import com.numberone.backend.domain.articleparticipant.entity.ArticleParticipant;
import com.numberone.backend.domain.articleparticipant.repository.ArticleParticipantRepository;
import com.numberone.backend.domain.comment.dto.request.CreateChildCommentRequest;
import com.numberone.backend.domain.comment.dto.response.CreateChildCommentResponse;
import com.numberone.backend.domain.comment.entity.CommentEntity;
import com.numberone.backend.domain.comment.repository.CommentRepository;
import com.numberone.backend.exception.notfound.NotFoundArticleException;
import com.numberone.backend.exception.notfound.NotFoundCommentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final ArticleParticipantRepository articleParticipantRepository;

    @Transactional
    public CreateChildCommentResponse createChildComment(
            Long articleId,
            Long parentCommentId,
            CreateChildCommentRequest request){

        Article article = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
        CommentEntity parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(NotFoundCommentException::new);

        CommentEntity childComment = commentRepository.save(new CommentEntity(request.getContent(), article));

        childComment.updateParent(parentComment);

        return CreateChildCommentResponse.of(childComment);
    }

}
