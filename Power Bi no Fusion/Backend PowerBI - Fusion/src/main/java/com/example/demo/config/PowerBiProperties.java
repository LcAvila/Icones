package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "powerbi")
public record PowerBiProperties(
        String tenantId,
        String clientId,
        String clientSecret,
        String workspaceId,
        String reportId,
        String datasetId
) {}
