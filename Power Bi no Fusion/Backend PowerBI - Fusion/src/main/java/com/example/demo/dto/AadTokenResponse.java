package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AadTokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") long expiresIn,
        @JsonProperty("token_type") String tokenType
) {}
