package com.backbone.phalanx.authentication.dto;

public record SignInRequest(
        String email,
        String password
) {
}
