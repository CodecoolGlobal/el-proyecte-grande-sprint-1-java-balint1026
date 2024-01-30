package com.codecool.puzzleshowdown.controller;

import com.codecool.puzzleshowdown.service.ServicePuzzle;
import com.codecool.puzzleshowdown.dto.puzzle.PuzzleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
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

    @PatchMapping("/api/puzzle/{puzzleId}")
    public void giveUpvote(@PathVariable int puzzleId){
        servicePuzzle.giveUpvoteToPuzzle(puzzleId);
    }
}
