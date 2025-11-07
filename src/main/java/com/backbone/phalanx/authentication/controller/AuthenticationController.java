package com.backbone.phalanx.authentication.controller;

import com.backbone.phalanx.authentication.dto.JwtAuthenticationResponse;
import com.backbone.phalanx.authentication.dto.JwtRefreshRequest;
import com.backbone.phalanx.authentication.dto.SignInRequest;
import com.backbone.phalanx.authentication.dto.SignUpRequest;
import com.backbone.phalanx.authentication.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/public")
@Tag(name = "Authentication", description = "Sign-up / Sign-in APIs")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/sign-up")
    @Operation(summary = "SignUp by User Credentials.", description = "Returns token for given user.")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping(value = "/sign-in")
    @Operation(summary = "SignIn by JWT token.", description = "Returns token for given user.")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @PostMapping(value = "/refresh")
    @Operation(
            summary = "Refresh access token based on refresh token.",
            description = "Returns brand new access token."
    )
    public JwtAuthenticationResponse refresh(@RequestBody @Valid JwtRefreshRequest request) {
        return authenticationService.refresh(request);
    }
}
