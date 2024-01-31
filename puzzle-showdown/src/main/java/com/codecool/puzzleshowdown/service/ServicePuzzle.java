package com.codecool.puzzleshowdown.service;

import com.codecool.puzzleshowdown.dto.puzzle.PuzzleDTO;
import org.springframework.stereotype.Service;

@Service
public interface ServicePuzzle {
    PuzzleDTO getPuzzle();
    void giveUpvoteToPuzzle(String puzzleId);

    void giveDownvoteToPuzzle(String puzzleId);

    String isValid(String puzzleId, String move, int step);
}
