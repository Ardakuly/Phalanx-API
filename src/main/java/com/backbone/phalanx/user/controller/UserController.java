package com.backbone.phalanx.user.controller;

import com.backbone.phalanx.user.dto.UserDto;
import com.backbone.phalanx.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
@Tag(name = "User information requests", description = "User related requests")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    @Operation(summary = "Retrieve users", description = "Retrieve all users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping
    @Operation(summary = "Retrieve user", description = "Retrieve user by token")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }

}
