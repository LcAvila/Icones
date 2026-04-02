package com.example.demo.dto;

import java.util.List;

public record GenerateTokenRequestV2(
        List<IdRef> datasets,
        List<IdRef> reports,
        List<IdRef> targetWorkspaces
) {
    public record IdRef(String id) {}
}
