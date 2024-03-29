package com.codecool.puzzleshowdown.repository;

import com.codecool.puzzleshowdown.repository.model.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {
}
