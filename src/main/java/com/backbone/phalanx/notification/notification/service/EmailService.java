package com.backbone.phalanx.notification.notification.service;

public interface EmailService {

    /**
     * Send verification mail to a user
     * @param email - user's email
     * @param verificationToken - verification token
     */
    void sendVerificationMail(String email, String verificationToken);

    /**
     * Send password reset mail.
     * @param email - user's email
     * @param code - code to reset password
     */
    void sendPasswordResetMail(String email, String code);
}