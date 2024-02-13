package com.codecool.puzzleshowdown.dao;

import com.codecool.puzzleshowdown.dao.model.Puzzle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PuzzleRepository extends JpaRepository<Puzzle, String> {
    @Query(value = "SELECT p FROM Puzzle AS p ORDER BY RANDOM() limit 1")
    Optional<Puzzle> getRandomPuzzle();
}
