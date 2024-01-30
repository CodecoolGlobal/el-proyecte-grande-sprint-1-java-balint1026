package com.codecool.puzzleshowdown.service;

import com.codecool.puzzleshowdown.dao.PuzzleDAO;
import com.codecool.puzzleshowdown.dao.model.Puzzle;
import com.codecool.puzzleshowdown.dto.puzzle.PuzzleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PuzzleService implements ServicePuzzle{
    private final PuzzleDAO puzzleDAO;

    @Autowired
    public PuzzleService(PuzzleDAO puzzleDAO) {
        this.puzzleDAO = puzzleDAO;
    }

    @Override
    public PuzzleDTO getPuzzle() {
        Puzzle puzzle = puzzleDAO.getPuzzle();
        return new PuzzleDTO(puzzle.puzzleId(), puzzle.fen(), puzzle.moves(), puzzle.rating(), puzzle.popularity());
    }

    @Override
    public void giveUpvoteToPuzzle(String puzzleId) {
        puzzleDAO.giveUpVote(puzzleId);
    }
}
