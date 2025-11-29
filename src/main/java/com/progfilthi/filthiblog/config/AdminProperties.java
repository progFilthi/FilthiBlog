package com.progfilthi.filthiblog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.admin")
public record AdminProperties(
        String username,
        String email,
        String password
) {
}
