package com.backbone.phalanx.authentication.service.implementation;

import com.backbone.phalanx.authentication.model.User;
import com.backbone.phalanx.authentication.service.EmailVerificationService;
import com.backbone.phalanx.authentication.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    @Transactional
    public String verifyEmail(String token) {
        String email = jwtService.extractEmail(token);
        User user = userService.loadUserByUsername(email);

        if (user.getVerificationToken() == null || !jwtService.isTokenValid(token, user)) {
            throw new RuntimeException("Email verification token expired!");
        }

        user.setVerificationToken(null);
        user.setIsEmailVerified(true);

        return "Email verification was successful!";
    }
}
