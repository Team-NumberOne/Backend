package com.numberone.backend.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "naverFeign", url = "${spring.naver.api-url}")
public interface NaverFeign {
    @GetMapping(value = "${spring.naver.token-info-url}")
    NaverIdDto getUserData(@RequestHeader(name = "Authorization") String token);
}
