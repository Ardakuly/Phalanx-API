package com.backbone.phalanx.authentication.service.implementation;


import com.backbone.phalanx.authentication.dto.JwtAuthenticationResponse;
import com.backbone.phalanx.authentication.dto.JwtRefreshRequest;
import com.backbone.phalanx.authentication.dto.SignInRequest;
import com.backbone.phalanx.authentication.dto.SignUpRequest;
import com.backbone.phalanx.user.model.User;
import com.backbone.phalanx.authentication.service.AuthenticationService;
import com.backbone.phalanx.user.service.UserService;
import com.backbone.phalanx.authorization.model.Role;
import com.backbone.phalanx.exception.RefreshTokenIsInvalidException;
import com.backbone.phalanx.exception.UserIsNotEnabledException;
import com.backbone.phalanx.notification.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.backbone.phalanx.util.CommonDateUtils.getCurrentDay;
import static com.backbone.phalanx.util.CommonDateUtils.getCurrentWeek;


@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    @Transactional
    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setIsBlocked(false);
        user.setCreatedAt(LocalDateTime.now());
        user.setIsBlocked(false);
        user.setIsEmailVerified(true);
        user.setRole(Role.EMPLOYER);
        // TODO: Set Profile Picture URL

        userService.create(user);

        String accessJwt = jwtService.generateToken(user, getCurrentDay());
        String refreshJwt = jwtService.generateToken(user, getCurrentWeek());

        user.setVerificationToken(accessJwt);

        userService.updateUser(user);

        emailService.sendVerificationMail(request.email(), accessJwt);

        return new JwtAuthenticationResponse(accessJwt, refreshJwt);
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {

        User user = userService.loadUserByUsername(request.email());

        if (!user.isEnabled()) {
            throw new UserIsNotEnabledException(request.email());
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(), request.password(), user.getAuthorities()
        ));

        String accessJwt = jwtService.generateToken(user, getCurrentDay());
        String refreshJwt = jwtService.generateToken(user, getCurrentWeek());

        return new JwtAuthenticationResponse(accessJwt, refreshJwt);
    }

    @Override
    public JwtAuthenticationResponse refresh(JwtRefreshRequest request) {
        String refreshToken = request.refreshToken();

        String email = jwtService.extractEmail(refreshToken);

        User user = userService.loadUserByUsername(email);

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new RefreshTokenIsInvalidException(user.getEmail());
        }

        if (!user.isEnabled()) {
            throw new UserIsNotEnabledException(email);
        }

        String newAccessJwt = jwtService.generateToken(user, getCurrentDay());
        String newRefreshJwt = jwtService.generateToken(user, getCurrentWeek());

        return new JwtAuthenticationResponse(newAccessJwt, newRefreshJwt);
    }
}
