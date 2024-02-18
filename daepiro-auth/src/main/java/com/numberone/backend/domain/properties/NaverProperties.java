package com.numberone.backend.domain.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.naver")
public class NaverProperties {
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String token_api_url;
    private String user_api_url;
}
