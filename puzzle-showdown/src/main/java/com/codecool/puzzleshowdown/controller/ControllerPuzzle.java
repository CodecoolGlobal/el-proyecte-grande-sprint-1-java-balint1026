package com.codecool.puzzleshowdown.controller;

import com.codecool.puzzleshowdown.dto.puzzle.PuzzleDTO;
import com.codecool.puzzleshowdown.repository.model.Puzzle;
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
    public PuzzleDTO getRandomPuzzleByRating(@RequestParam(defaultValue = "0") int min, @RequestParam(defaultValue = "10000") int max){
        return puzzleService.getRandomPuzzle(min, max);
    }

    @GetMapping("/random/{userId}")
    public PuzzleDTO getRandomPuzzleForUser(@PathVariable long userId,@RequestParam int min, @RequestParam int max){
        return puzzleService.getPuzzleForUser(userId, min, max);
    }

    @GetMapping("/valid/{puzzleId}/{move}/{step}")
    public String checkValidMove(@PathVariable String puzzleId, @PathVariable String move, @PathVariable int step){
        return puzzleService.isValidStep(puzzleId, move, step);
    }

    @GetMapping("/hint/{puzzleId}/{moveCount}")
        public String getHint(@PathVariable String puzzleId, @PathVariable int moveCount){
        return puzzleService.getHint(puzzleId, moveCount);
    }

    @GetMapping("/next/{first}/{step}/{count}")
    public PuzzleDTO getNextPuzzleForRace(@PathVariable int first, @PathVariable int step, @PathVariable int count) {
        return puzzleService.getNextPuzzleForRace(first - 1, step, count);
    }

    @PatchMapping("/{puzzleId}/popularity/{vote}")
    public void updatePopularity(@PathVariable String puzzleId, @PathVariable int vote){
        puzzleService.updatePopularity(puzzleId, vote);
    }
}
