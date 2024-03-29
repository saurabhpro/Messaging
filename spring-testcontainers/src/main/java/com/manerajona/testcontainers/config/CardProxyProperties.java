package com.manerajona.testcontainers.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ws.card")
public record CardProxyProperties(String baseUrl, String validateUri) {

    public String validateUri() {
        return "%s/%s".formatted(this.baseUrl, this.validateUri);
    }
}