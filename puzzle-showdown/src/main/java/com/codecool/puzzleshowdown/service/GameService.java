package com.codecool.puzzleshowdown.service;

import com.codecool.puzzleshowdown.repository.GameRepository;
import com.codecool.puzzleshowdown.repository.PuzzleRepository;
import com.codecool.puzzleshowdown.repository.UserRepository;
import com.codecool.puzzleshowdown.repository.model.Game;
import com.codecool.puzzleshowdown.repository.model.Puzzle;
import com.codecool.puzzleshowdown.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {
    private GameRepository gameRepository;
    private UserService userService;
    private PuzzleService puzzleService;

    @Autowired
    public GameService(GameRepository gameRepository, UserService userService, PuzzleService puzzleService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.puzzleService = puzzleService;

    }

    public void uploadGame(List<Long> playersId, List<String> puzzlesId){
        List<User> users = getUsersById(playersId);
        Game game = new Game(users, users.getFirst(), LocalDate.now(), getPuzzles(puzzlesId));
        gameRepository.save(game);
    }

    private List<Puzzle> getPuzzles(List<String> puzzlesId) {
        List<Puzzle> puzzles = new ArrayList<>();
        for(String id : puzzlesId){
            puzzles.add(puzzleService.getPuzzleById(id));
        }
        return puzzles;
    }

    private List<User> getUsersById(List<Long> players) {
        List<User> users = new ArrayList<>();
        for (long id : players){
            users.add(userService.getUser(id));
        }
        return users;
    }
}
