package com.numberone.backend.domain.like.controller;

import com.numberone.backend.support.redis.like.service.RedisLockLikeFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {
    private final RedisLockLikeFacade redisLockLikeFacade;


}
