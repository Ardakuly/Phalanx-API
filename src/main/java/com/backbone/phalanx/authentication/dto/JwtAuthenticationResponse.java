package com.backbone.phalanx.authentication.dto;

public record JwtAuthenticationResponse(
        String accessToken,
        String refreshToken
) {
}
