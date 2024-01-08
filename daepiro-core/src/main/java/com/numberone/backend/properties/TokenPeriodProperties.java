package com.numberone.backend.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.token")
public class TokenPeriodProperties {
    private Long access;
    private Long refresh;
}
