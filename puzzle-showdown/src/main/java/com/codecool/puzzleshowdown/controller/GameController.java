package com.codecool.puzzleshowdown.controller;

import com.codecool.puzzleshowdown.dto.game.GameDTO;
import com.codecool.puzzleshowdown.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {
    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/api/gametest")
    public void insertGame(@RequestBody GameDTO gameDTO){
        gameService.uploadGame(gameDTO.users(), gameDTO.puzzles());
    }
}
