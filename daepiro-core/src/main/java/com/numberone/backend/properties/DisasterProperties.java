package com.numberone.backend.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Data
@Service
@ConfigurationProperties(prefix = "disaster")
public class DisasterProperties {
    private String apiUrl;
    private String secretKey;
    private String crawlingUrl;
}
