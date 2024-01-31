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
        return new PuzzleDTO(
                puzzle.puzzleId(),
                puzzle.fen(),
                puzzle.moves().split(" ")[0],
                puzzle.rating(),
                puzzle.popularity());
    }

    @Override
    public void giveUpvoteToPuzzle(String puzzleId) {
        puzzleDAO.giveUpVote(puzzleId);
    }

    @Override
    public void giveDownvoteToPuzzle(String puzzleId) {
        puzzleDAO.giveDownVote(puzzleId);
    }

    @Override
    public String isValid(String puzzleId, String move, int step) {
        step--;
        Puzzle puzzle = puzzleDAO.checkValidMove(puzzleId);
        String[] moves = puzzle.moves().split(" ");
        if (moves.length > step && step >= 0){
            if(moves[step].equals(move)){
                return moves[++step];
            }
        }
        if (moves.length < step)
            return "Win";
        return null;
    }
}
