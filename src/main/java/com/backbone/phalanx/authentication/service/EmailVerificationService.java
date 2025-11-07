package com.backbone.phalanx.authentication.service;

public interface EmailVerificationService {

    /**
     * Load user by email.
     * @param verificationToken
     * @return verification status
     */
    String verifyEmail(String verificationToken);
}
