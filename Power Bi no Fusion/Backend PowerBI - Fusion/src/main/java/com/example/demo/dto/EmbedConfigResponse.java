package com.example.demo.dto;

public record EmbedConfigResponse(
        String reportId,
        String embedUrl,
        String embedToken,
        String expiration
) {}
