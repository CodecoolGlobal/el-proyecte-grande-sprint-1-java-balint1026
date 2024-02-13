package com.codecool.puzzleshowdown.repository;

import com.codecool.puzzleshowdown.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String userName);
    Optional<User> findByEmailOrUserName(String email, String userName);
}
