package com.numberone.backend.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoFeign", url = "https://kapi.kakao.com")
public interface KakaoFeign {
    @GetMapping(value = "/v1/user/access_token_info")
    KakaoIdDto getUserData(@RequestHeader(name = "Authorization") String token);
}
