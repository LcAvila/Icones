package com.example.demo.dto;

public record GenerateTokenResponse(
        String token,
        String tokenId,
        String expiration
) {}
