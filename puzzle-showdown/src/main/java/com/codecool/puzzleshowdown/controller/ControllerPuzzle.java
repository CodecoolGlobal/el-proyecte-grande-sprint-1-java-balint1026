package com.codecool.puzzleshowdown.controller;

import com.codecool.puzzleshowdown.service.ServicePuzzle;
import com.codecool.puzzleshowdown.dto.puzzle.PuzzleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ControllerPuzzle {

    private final ServicePuzzle servicePuzzle;

    @Autowired
    public ControllerPuzzle(ServicePuzzle servicePuzzle) {
        this.servicePuzzle = servicePuzzle;
    }

    @GetMapping("/api/puzzle")
    public List<PuzzleDTO> getPuzzle(){
        return servicePuzzle.getPuzzle();
    }

    @GetMapping("/api/puzzle/random")
    public PuzzleDTO getRandomPuzzle(){
        return servicePuzzle.getRandom();
    }
    @PatchMapping("/api/puzzle/{puzzleId}")
    public void giveUpvote(@PathVariable int puzzleId){
        servicePuzzle.giveUpvoteToPuzzle(puzzleId);
    }
}
