package com.backbone.phalanx.authentication.dto;

public record SignUpRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
