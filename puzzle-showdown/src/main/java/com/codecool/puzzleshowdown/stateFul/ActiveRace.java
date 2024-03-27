package com.codecool.puzzleshowdown.stateFul;

import org.springframework.web.socket.WebSocketSession;

import java.util.Set;

public record ActiveRace(
        String raceId,
        String spectateId,
        String hostUsername,
        Set<PlayerInActiveRace> players,
        Set<WebSocketSession> spectators,
        int timeframe,
        GameState gameState,
        int first,
        int step

) {}
