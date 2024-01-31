package com.codecool.puzzleshowdown.dao;

import com.codecool.puzzleshowdown.dao.model.Puzzle;

public interface PuzzleDAO {
    Puzzle getPuzzle();
    void giveUpVote(String puzzleId);

    void giveDownVote(String puzzleId);

    Puzzle checkValidMove(String puzzleId);
}
