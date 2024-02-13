package com.codecool.puzzleshowdown.dao;

import com.codecool.puzzleshowdown.dao.model.Puzzle;

public interface PuzzleDAO {
    Puzzle getRandomPuzzle();
    void updatePopularity(String puzzleId, int vote);
    Puzzle getPuzzle(String puzzleId);
}
