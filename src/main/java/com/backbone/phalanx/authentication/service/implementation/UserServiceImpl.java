package com.backbone.phalanx.authentication.service.implementation;

import com.backbone.phalanx.authentication.UserRepository;
import com.backbone.phalanx.authentication.model.User;
import com.backbone.phalanx.authentication.service.UserService;
import com.backbone.phalanx.authorization.model.Role;
import com.backbone.phalanx.exception.UserAlreadyExistsException;
import com.backbone.phalanx.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void create(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException(user.getEmail());
        }

        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    @Transactional
    public void changeRole(String email, Role role) {
        User existingUser = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(email)
        );

        existingUser.setRole(role);
    }

    @Override
    @Transactional
    public void enableUser(String email) {
        User existingUser = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(email)
        );

        existingUser.setIsBlocked(false);
    }

    @Override
    @Transactional
    public void disableUser(String email) {
        User existingUser = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(email)
        );

        existingUser.setIsBlocked(true);
    }
}