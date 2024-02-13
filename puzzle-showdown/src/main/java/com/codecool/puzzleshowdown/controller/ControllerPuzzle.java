package com.codecool.puzzleshowdown.controller;

import com.codecool.puzzleshowdown.dto.puzzle.PuzzleDTO;
import com.codecool.puzzleshowdown.service.PuzzleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/puzzle")
public class ControllerPuzzle {
    private final PuzzleService puzzleService;

    @Autowired
    public ControllerPuzzle(PuzzleService puzzleService) {
        this.puzzleService = puzzleService;
    }

    @GetMapping
    public PuzzleDTO getPuzzle(@RequestParam int min, @RequestParam int max){
        return puzzleService.getRandomPuzzle(min, max);
    }

    @GetMapping("/valid/{puzzleId}/{move}/{step}")
    public String checkValidMove(@PathVariable String puzzleId, @PathVariable String move, @PathVariable int step){
        return puzzleService.isValidStep(puzzleId, move, step);
    }

    @GetMapping("/hint/{puzzleId}/{moveCount}")
        public String getHint(@PathVariable String puzzleId, @PathVariable int moveCount){
        return puzzleService.getHint(puzzleId, moveCount);
    }


    @PatchMapping("/{puzzleId}/popularity/{vote}")
    public void givePopularity(@PathVariable String puzzleId, @PathVariable int vote){
        puzzleService.updatePopularity(puzzleId, vote);
    }
}
