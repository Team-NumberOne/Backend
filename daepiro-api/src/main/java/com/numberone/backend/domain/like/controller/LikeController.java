package com.numberone.backend.domain.like.controller;

import com.numberone.backend.domain.like.dto.response.ArticleLikeResponse;
import com.numberone.backend.domain.like.dto.response.CommentLikeResponse;
import com.numberone.backend.domain.like.service.RedisLockLikeFacade;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {
    private final RedisLockLikeFacade redisLockLikeFacade;

    @Operation(summary = "게시글 좋아요 추가")
    @PutMapping("articles/{article-id}/add")
    public ResponseEntity<ArticleLikeResponse> addArticleLike(
            @PathVariable("article-id") Long articleId) throws InterruptedException {
      return ResponseEntity.ok(redisLockLikeFacade.increaseArticleLike(articleId));
    }

    @Operation(summary = "게시글 좋아요 취소")
    @PutMapping("articles/{article-id}/cancel")
    public ResponseEntity<ArticleLikeResponse> cancelArticleLike(
            @PathVariable("article-id") Long articleId) throws InterruptedException {
        return ResponseEntity.ok(redisLockLikeFacade.decreaseArticleLike(articleId));
    }

    @Operation(summary = "댓글 좋아요 추가")
    @PutMapping("comments/{comment-id}/add")
    public ResponseEntity<CommentLikeResponse> addCommentLike(
            @PathVariable("comment-id") Long commentId) throws InterruptedException {
        return ResponseEntity.ok(redisLockLikeFacade.increaseCommentLike(commentId));
    }

    @Operation(summary = "댓글 좋아요 취소")
    @PutMapping("comments/{comment-id}/cancel")
    public ResponseEntity<CommentLikeResponse> cancelCommentLike(
            @PathVariable("comment-id") Long commentId) throws InterruptedException {
        return ResponseEntity.ok(redisLockLikeFacade.decreaseCommentLike(commentId));
    }


}
