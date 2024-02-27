package com.codecool.puzzleshowdown.stateFul;

import org.springframework.web.socket.WebSocketSession;

public record PlayerInActiveRace(WebSocketSession socketSession, String username) {
}
