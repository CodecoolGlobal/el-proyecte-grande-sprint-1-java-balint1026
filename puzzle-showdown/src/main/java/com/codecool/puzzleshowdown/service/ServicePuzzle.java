package com.codecool.puzzleshowdown.service;

import com.codecool.puzzleshowdown.dto.puzzle.PuzzleDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServicePuzzle {
    PuzzleDTO getPuzzle();
    void giveUpvoteToPuzzle(String puzzleId);
}
