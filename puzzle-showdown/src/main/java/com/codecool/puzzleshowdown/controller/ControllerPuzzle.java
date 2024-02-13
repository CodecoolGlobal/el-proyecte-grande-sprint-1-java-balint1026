package com.codecool.puzzleshowdown.controller;

import com.codecool.puzzleshowdown.dto.puzzle.PuzzleDTO;
import com.codecool.puzzleshowdown.service.PuzzleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ControllerPuzzle {

    private final PuzzleService puzzleService;

    @Autowired
    public ControllerPuzzle(PuzzleService puzzleService) {
        this.puzzleService = puzzleService;
    }

    @GetMapping("/api/puzzle")
    public PuzzleDTO getPuzzle(){
        return puzzleService.getRandomPuzzle();
    }

    @GetMapping("/api/puzzle/valid/{puzzleId}/{move}/{step}")
    public String checkValidMove(@PathVariable String puzzleId, @PathVariable String move, @PathVariable int step){
        return puzzleService.isValidStep(puzzleId, move, step);
    }

    @PatchMapping("/api/puzzle/{puzzleId}/popularity/{vote}")
    public void givePopularity(@PathVariable String puzzleId, @PathVariable int vote){
        puzzleService.updatePopularity(puzzleId, vote);
    }
}
