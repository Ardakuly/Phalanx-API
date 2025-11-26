package com.backbone.phalanx.user.service;

import com.backbone.phalanx.authorization.model.Role;
import com.backbone.phalanx.user.dto.UserDto;
import com.backbone.phalanx.user.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    /**
     * Retrieves a list of all users.
     *
     * @return a list of all users as UserDto objects
     */
    List<UserDto> getAllUsers();
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
     * Retrieves a user by their username (email) and returns the corresponding UserDto object.
     *
     * @param email the email address (username) of the user to retrieve
     * @return the user details wrapped in a UserDto object, or null if no user is found
     */
    UserDto getUserByUsername(String email);

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