package com.codecool.puzzleshowdown.service;

import com.codecool.puzzleshowdown.repository.RaceRepository;
import com.codecool.puzzleshowdown.repository.model.Race;
import com.codecool.puzzleshowdown.repository.model.Puzzle;
import com.codecool.puzzleshowdown.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RaceService {
    private final RaceRepository raceRepository;
    private final UserService userService;
    private final PuzzleService puzzleService;

    @Autowired
    public RaceService(RaceRepository raceRepository, UserService userService, PuzzleService puzzleService) {
        this.raceRepository = raceRepository;
        this.userService = userService;
        this.puzzleService = puzzleService;

    }

    public void uploadGame(List<Long> playersId, List<String> puzzlesId){
        List<User> users = getUsersById(playersId);
        Race race = new Race(users, users.getFirst(), LocalDate.now(), getPuzzles(puzzlesId));
        raceRepository.save(race);
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
