package com.numberone.backend.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoFeign", url = "${spring.kakao.api-url}")
public interface KakaoFeign {
    @GetMapping(value = "${spring.kakao.token-info-url}")
    KakaoIdDto getUserData(@RequestHeader(name = "Authorization") String token);
}
