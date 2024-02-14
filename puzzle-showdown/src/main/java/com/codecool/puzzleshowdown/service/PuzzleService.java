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
        puzzleRepository.updatePuzzleByPuzzleId(puzzleId, vote);
    }

    private boolean isInIndex(String[] array, int index){
        return index < array.length && index >= 0;
    }

    public String isValidStep(String puzzleId, String move, int step) {
        String[] moves = getMoves(puzzleId);
        if (isInIndex(moves, step)){
            if(moves[step].equals(move)){
                if (step + 1 == moves.length){
                    return "win";
                }
                return moves[step + 1];
            }
        }
        return null;
    }

    public String getHint(String puzzleId, int step){
        String[] moves = getMoves(puzzleId);
        if (step < moves.length && step >= 0)
            return moves[step].substring(0,2);
        return "";
    }

    public Puzzle getPuzzleById(String id){
        Optional<Puzzle> response = puzzleRepository.findById(id);
        return response.orElse(null);
    }

    private String[] getMoves(String id){
        Puzzle puzzle = getPuzzleById(id);
        return puzzle.getMoves().split(" ");
    }
}
