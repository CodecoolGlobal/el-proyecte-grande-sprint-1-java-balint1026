package com.codecool.puzzleshowdown.repository;

import com.codecool.puzzleshowdown.repository.model.Puzzle;
import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PuzzleRepository extends JpaRepository<Puzzle, String> {
    @Query(value = "SELECT p FROM Puzzle AS p where p.rating between :min and :max ORDER BY RANDOM() limit 1")
    Optional<Puzzle> getRandomPuzzle(int min, int max);
    @Transactional
    @Modifying
    @Query(value = "UPDATE Puzzle p SET p.popularity = p.popularity + :newValue WHERE p.puzzleid = :id")
    void updatePuzzleByPuzzleId(String id, int newValue);

    @Query
    Optional<Puzzle> findFirstByRatingIsGreaterThanOrderByRating(int minRating, OffsetScrollPosition scrollPosition);
}
