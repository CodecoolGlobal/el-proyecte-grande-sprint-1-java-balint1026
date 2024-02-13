package com.codecool.puzzleshowdown.service;

import com.codecool.puzzleshowdown.repository.PuzzleRepository;
import com.codecool.puzzleshowdown.repository.model.Puzzle;
import com.codecool.puzzleshowdown.dto.puzzle.PuzzleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PuzzleService{
    private final PuzzleRepository puzzleRepository;
    @Autowired
    public PuzzleService(PuzzleRepository puzzleRepository) {
        this.puzzleRepository = puzzleRepository;
    }

    public PuzzleDTO getRandomPuzzle(int min, int max) {
        Optional<Puzzle> respond = puzzleRepository.getRandomPuzzle(min, max);
        if (respond.isEmpty()) return null;
        Puzzle puzzles = respond.get();
        return new PuzzleDTO(
                puzzles.getPuzzleid(),
                puzzles.getFen(),
                puzzles.getMoves().split(" ")[0],
                puzzles.getRating(),
                puzzles.getPopularity());
    }

    public void updatePopularity(String puzzleId, int vote) {
        //puzzleRepository.updatePopularity(puzzleId, vote);
        throw new RuntimeException("Not implemented yet");
    }

    public String isValidStep(String puzzleId, String move, int step) {
        Optional<Puzzle> respond = puzzleRepository.findById(puzzleId);
        if (respond.isEmpty()) return null;

        Puzzle puzzle = respond.get();
        String[] moves = puzzle.getMoves().split(" ");
        if (step > 0 && step + 1 < moves.length){
            if(moves[step].equals(move)){
                return moves[++step];
            }
        }
        if (step + 1 == moves.length){
            return "win";
        }
        return null;
    }
}
