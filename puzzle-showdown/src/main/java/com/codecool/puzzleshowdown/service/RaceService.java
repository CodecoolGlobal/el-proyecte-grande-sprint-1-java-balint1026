package com.codecool.puzzleshowdown.service;

import com.codecool.puzzleshowdown.repository.RaceRepository;
import com.codecool.puzzleshowdown.repository.model.Race;
import com.codecool.puzzleshowdown.repository.model.Puzzle;
import com.codecool.puzzleshowdown.repository.model.User;
import com.codecool.puzzleshowdown.stateFul.ActiveRace;
import com.codecool.puzzleshowdown.stateFul.PlayerInActiveRace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDate;
import java.util.*;

@Service
public class RaceService {
    private final RaceRepository raceRepository;
    private final UserService userService;
    private final PuzzleService puzzleService;

    private final Map<String, ActiveRace> activeRaces = new HashMap<>();
    private final Map<String, String> activeSpectates = new HashMap<>();

    @Autowired
    public RaceService(RaceRepository raceRepository, UserService userService, PuzzleService puzzleService) {
        this.raceRepository = raceRepository;
        this.userService = userService;
        this.puzzleService = puzzleService;
    }

    public boolean isRaceIdValid(String raceId) {
        return activeRaces.get(raceId) != null;
    }

    public void addActiveRace(String raceId, String spectateId, ActiveRace activeRace) {
        activeRaces.put(raceId, activeRace);
        activeSpectates.put(spectateId, raceId);
    }

    public ActiveRace getActiveRaceByRaceId(String raceId) {
        return new ActiveRace(
                activeRaces.get(raceId).raceId(),
                activeRaces.get(raceId).spectateId(),
                activeRaces.get(raceId).hostUsername(),
                activeRaces.get(raceId).players(),
                activeRaces.get(raceId).spectators(),
                activeRaces.get(raceId).timeframe(),
                activeRaces.get(raceId).gameState(),
                activeRaces.get(raceId).first(),
                activeRaces.get(raceId).step()
        );
    }

    public ActiveRace getActiveRaceBySpectateId(String spectateId) {
        return new ActiveRace(
                activeRaces.get(activeSpectates.get(spectateId)).raceId(),
                activeRaces.get(activeSpectates.get(spectateId)).spectateId(),
                activeRaces.get(activeSpectates.get(spectateId)).hostUsername(),
                activeRaces.get(activeSpectates.get(spectateId)).players(),
                activeRaces.get(activeSpectates.get(spectateId)).spectators(),
                activeRaces.get(activeSpectates.get(spectateId)).timeframe(),
                activeRaces.get(activeSpectates.get(spectateId)).gameState(),
                activeRaces.get(activeSpectates.get(spectateId)).first(),
                activeRaces.get(activeSpectates.get(spectateId)).step()
        );
    }

    public void addPlayerToActiveRace(String raceId, WebSocketSession socketSession, String username) {
        activeRaces.get(raceId).players().add(new PlayerInActiveRace(socketSession, username));
    }

    public void startActiveRace(String raceId) {
        activeRaces.get(raceId).gameState().setPending(false);
    }

    public int getActiveRaceFirstPuzzle(String raceId) {
        return activeRaces.get(raceId).first();
    }

    public int getActiveRacePuzzleStep(String raceId) {
        return activeRaces.get(raceId).step();
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
