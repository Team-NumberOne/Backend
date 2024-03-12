package com.numberone.backend.config;

import com.numberone.backend.feign.KakaoFeign;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.numberone.backend")
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class FeignConfig {
}
