package com.backbone.phalanx.user.repository;

import com.backbone.phalanx.authorization.model.Role;
import com.backbone.phalanx.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Stream<User> findAllByRole(Role role);
}