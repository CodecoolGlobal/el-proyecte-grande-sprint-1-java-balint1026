package com.codecool.puzzleshowdown.service;

import com.codecool.puzzleshowdown.dao.PuzzleDAO;
import com.codecool.puzzleshowdown.dao.model.Puzzle;
import com.codecool.puzzleshowdown.dto.puzzle.PuzzleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PuzzleService{
    private final PuzzleDAO puzzleDAO;

    @Autowired
    public PuzzleService(PuzzleDAO puzzleDAO) {
        this.puzzleDAO = puzzleDAO;
    }

    public PuzzleDTO getRandomPuzzle() {
        Puzzle puzzle = puzzleDAO.getRandomPuzzle();
        return new PuzzleDTO(
                puzzle.puzzleId(),
                puzzle.fen(),
                puzzle.moves().split(" ")[0],
                puzzle.rating(),
                puzzle.popularity());
    }

    public void updatePopularity(String puzzleId, int vote) {
        puzzleDAO.updatePopularity(puzzleId, vote);
    }

    public String isValidStep(String puzzleId, String move, int step) {
        try{
            Puzzle puzzle = puzzleDAO.getPuzzle(puzzleId);
            String[] moves = puzzle.moves().split(" ");
            if (step > 0){
                if(moves[step].equals(move)){
                    return moves[++step];
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            return "win";
        }
        return null;
    }
}
