package com.codecool.puzzleshowdown.dao;

import com.codecool.puzzleshowdown.dao.model.Puzzle;

public interface PuzzleDAO {
    Puzzle getPuzzle();
    void givePopularity(String puzzleId, int vote);
    Puzzle checkValidMove(String puzzleId);
}
