package com.codecool.puzzleshowdown.controller;

import com.codecool.puzzleshowdown.dto.race.NewRaceDTO;
import com.codecool.puzzleshowdown.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RaceController {
    private final RaceService raceService;

    @Autowired
    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @PostMapping("/api/gametest")
    public void insertGame(@RequestBody NewRaceDTO newRaceDTO){
        raceService.uploadGame(newRaceDTO.users(), newRaceDTO.puzzles());
    }
}
