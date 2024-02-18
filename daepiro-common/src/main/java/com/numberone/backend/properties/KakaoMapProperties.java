package com.numberone.backend.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.kakao.map")
public class KakaoMapProperties {
    private String client_id;
    private String mapApiUrl;
}
