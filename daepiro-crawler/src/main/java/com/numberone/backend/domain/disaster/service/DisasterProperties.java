package com.numberone.backend.domain.disaster.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "disaster")
public class DisasterProperties {
    private String apiUrl;
    private String secretKey;
    private String crawlingUrl;
}
