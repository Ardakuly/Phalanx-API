package com.backbone.phalanx.authorization.controller;

import com.backbone.phalanx.user.service.UserService;
import com.backbone.phalanx.authorization.model.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
@Tag(name = "Authorization", description = "User's role operations APIs.")
@RequiredArgsConstructor
@Slf4j
public class AuthorizationController {

    private final UserService userService;

    @PatchMapping(value = "/{email}/role")
    @PreAuthorize("hasAuthority('EMPLOYER')")
    @Operation(summary = "Change the role of user", description = "Employer can change the role of user")
    public ResponseEntity<?> changeRole(@PathVariable("email") String email, @RequestParam("role") Role role) {
        userService.changeRole(email, role);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{email}/enable")
    @PreAuthorize("hasAuthority('EMPLOYER')")
    @Operation(summary = "Employer can enable user", description = "Employer can enable employee")
    public ResponseEntity<?> enableUser(@PathVariable("email") String email) {
        userService.enableUser(email);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{email}/disable")
    @PreAuthorize("hasAuthority('EMPLOYER')")
    @Operation(summary = "Employer can disable user", description = "Employer can disable employee")
    public ResponseEntity<?> disableUser(@PathVariable("email") String email) {
        userService.disableUser(email);
        return ResponseEntity.ok().build();
    }
}

