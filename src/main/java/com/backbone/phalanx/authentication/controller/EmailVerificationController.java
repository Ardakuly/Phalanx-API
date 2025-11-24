package com.backbone.phalanx.authentication.controller;

import com.backbone.phalanx.authentication.service.EmailVerificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/public/user/email")
@Tag(name = "Email operations for user account", description = "Email verification and other APIs")
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping(value = "/verify")
    public ResponseEntity<String> verifyEmail(String token) {
        return new ResponseEntity<>(emailVerificationService.verifyEmail(token), HttpStatus.OK);
    }

    // TODO: ADD VERIFY EMAIL FOR FORGET PASSWORD
}