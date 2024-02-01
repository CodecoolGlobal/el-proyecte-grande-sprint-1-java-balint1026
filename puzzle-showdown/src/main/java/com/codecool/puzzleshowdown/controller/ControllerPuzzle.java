package com.codecool.puzzleshowdown.controller;

import com.codecool.puzzleshowdown.dto.puzzle.PuzzleDTO;
import com.codecool.puzzleshowdown.service.ServicePuzzle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ControllerPuzzle {

    private final ServicePuzzle servicePuzzle;

    @Autowired
    public ControllerPuzzle(ServicePuzzle servicePuzzle) {
        this.servicePuzzle = servicePuzzle;
    }

    @GetMapping("/api/puzzle")
    public PuzzleDTO getPuzzle(){
        return servicePuzzle.getPuzzle();
    }

    @GetMapping("/api/puzzle/valid/{puzzleId}/{move}/{step}")
    public String checkValidMove(@PathVariable String puzzleId, @PathVariable String move, @PathVariable int step){
        return servicePuzzle.isValid(puzzleId, move, step);
    }

    @PatchMapping("/api/puzzle/{puzzleId}/popularity/{vote}")
    public void givePopularity(@PathVariable String puzzleId, @PathVariable int vote){
        servicePuzzle.givePopularityToPuzzle(puzzleId, vote);
    }
}
