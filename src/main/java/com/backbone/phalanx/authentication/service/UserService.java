package com.backbone.phalanx.authentication.service;

import com.backbone.phalanx.authentication.model.User;
import com.backbone.phalanx.authorization.model.Role;
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

    /**
     * Updates the role of a user identified by their email address to the specified role.
     *
     * @param email the email address of the user whose role is to be changed
     * @param role the new role to be assigned to the user
     */
    void changeRole(String email, Role role);

    /**
     * Enables a user account identified by their email address.
     * This typically involves updating the user's status to mark their account as active and accessible.
     *
     * @param email the email address of the user to be enabled
     */
    void enableUser(String email);

    /**
     * Disables a user account identified by their email address.
     * This typically involves setting the user's status to indicate
     * that their account is no longer active or accessible.
     *
     * @param email the email address of the user to be disabled
     */
    void disableUser(String email);
}