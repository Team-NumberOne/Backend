package com.numberone.backend.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.kakao")
public class KakaoProperties {
    private String client_id;
    private String redirect_uri;
    private String token_api_url;
    private String user_api_url;
}
