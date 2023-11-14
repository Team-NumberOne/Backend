package com.numberone.backend.support.redis.like.service;

import com.numberone.backend.domain.article.service.ArticleService;
import com.numberone.backend.support.redis.like.repository.RedisLockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisLockLikeFacade {
    private final RedisLockRepository redisLockRepository;

    public void increaseArticleLike(Long articleId) throws InterruptedException {
        while (!redisLockRepository.lock(articleId)){
            Thread.sleep(100);
        }
        try {
            // todo: 좋아요 로직
        } catch (Exception e){
            throw new RuntimeException();
        } finally {
            redisLockRepository.unlock(articleId);
        }
    }

}
