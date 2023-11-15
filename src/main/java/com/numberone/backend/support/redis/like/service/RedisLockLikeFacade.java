package com.numberone.backend.support.redis.like.service;

import com.numberone.backend.domain.like.dto.response.response.ArticleLikeResponse;
import com.numberone.backend.domain.like.dto.response.response.CommentLikeResponse;
import com.numberone.backend.domain.like.service.LikeService;
import com.numberone.backend.support.redis.like.repository.RedisLockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisLockLikeFacade {
    private final RedisLockRepository redisLockRepository;
    private final LikeService likeService;

    public ArticleLikeResponse increaseArticleLike(Long articleId) throws InterruptedException {
        while (!redisLockRepository.lock(articleId)) {
            Thread.sleep(100);
        }
        try {
            Integer likeCount = likeService.increaseArticleLike(articleId);
            return ArticleLikeResponse.builder()
                    .currentLikeCount(likeCount)
                    .articleId(articleId)
                    .build();
        } finally {
            redisLockRepository.unlock(articleId);
        }
    }

    public ArticleLikeResponse decreaseArticleLike(Long articleId) throws InterruptedException {
        while (!redisLockRepository.lock(articleId)) {
            Thread.sleep(100);
        }
        try {
            Integer likeCount = likeService.decreaseArticleLike(articleId);
            return ArticleLikeResponse.builder()
                    .currentLikeCount(likeCount)
                    .articleId(articleId)
                    .build();
        } finally {
            redisLockRepository.unlock(articleId);
        }
    }

    public CommentLikeResponse increaseCommentLike(Long commentId) throws InterruptedException {
        while (!redisLockRepository.lock(commentId)) {
            Thread.sleep(100);
        }
        try {
            Integer likeCount = likeService.increaseCommentLike(commentId);
            return CommentLikeResponse.builder()
                    .currentLikeCount(likeCount)
                    .commentId(commentId)
                    .build();
        } finally {
            redisLockRepository.unlock(commentId);
        }
    }

    public CommentLikeResponse decreaseCommentLike(Long commentId) throws InterruptedException {
        while (!redisLockRepository.lock(commentId)) {
            Thread.sleep(100);
        }
        try {
            Integer likeCount = likeService.decreaseCommentLike(commentId);
            return CommentLikeResponse.builder()
                    .currentLikeCount(likeCount)
                    .commentId(commentId)
                    .build();
        } finally {
            redisLockRepository.unlock(commentId);
        }
    }


}
