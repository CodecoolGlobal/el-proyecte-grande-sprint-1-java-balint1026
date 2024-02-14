package com.codecool.puzzleshowdown.repository;

import com.codecool.puzzleshowdown.repository.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}
