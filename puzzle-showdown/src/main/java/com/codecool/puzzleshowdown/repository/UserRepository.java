package com.codecool.puzzleshowdown.repository;

import com.codecool.puzzleshowdown.repository.model.Puzzle;
import com.codecool.puzzleshowdown.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUserName(String userName);

    Optional<User> findByEmailOrUserName(String email, String userName);

    List<User> findByOrderByRatingDescUserNameAsc();

}
