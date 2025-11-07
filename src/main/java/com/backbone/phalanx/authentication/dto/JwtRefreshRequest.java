package com.backbone.phalanx.authentication.dto;

public record JwtRefreshRequest(
        String refreshToken
) {
}
