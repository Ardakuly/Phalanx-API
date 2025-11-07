package com.backbone.phalanx.authentication.service.implementation;

import com.backbone.phalanx.authentication.UserRepository;
import com.backbone.phalanx.authentication.model.User;
import com.backbone.phalanx.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void create(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User with email - " + email  + " not found.")
        );
    }
}