package com.backbone.phalanx.authentication.service;

import com.backbone.phalanx.authentication.dto.JwtAuthenticationResponse;
import com.backbone.phalanx.authentication.dto.JwtRefreshRequest;
import com.backbone.phalanx.authentication.dto.SignInRequest;
import com.backbone.phalanx.authentication.dto.SignUpRequest;

public interface AuthenticationService {

    /**
     * Sign up a new user.
     * @param request
     * @return user's access token and refresh token
     */
    JwtAuthenticationResponse signUp(SignUpRequest request);

    /**
     * Sign in an existing user.
     * @param request
     * @return user's access token and refresh token
     */
    JwtAuthenticationResponse signIn(SignInRequest request);

    /**
     * Refresh access token based on refresh token.
     * @param request
     * @return user's new access token and refresh token
     */
    JwtAuthenticationResponse refresh(JwtRefreshRequest request);
}
