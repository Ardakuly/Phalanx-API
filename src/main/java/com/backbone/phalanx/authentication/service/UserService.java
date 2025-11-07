package com.backbone.phalanx.authentication.service;

import com.backbone.phalanx.authentication.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    /**
     * Create a new user.
     * @param user
     */
    void create(User user);

    /**
     * Update user's details.
     * @param user
     */
    void updateUser(User user);

    /**
     * Load user by email.
     * @param email
     * @return requested user
     */
    User loadUserByUsername(String email);
}