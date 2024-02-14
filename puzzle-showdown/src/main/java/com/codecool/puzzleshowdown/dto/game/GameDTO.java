package com.codecool.puzzleshowdown.dto.game;

import java.util.List;

public record GameDTO(List<Long> users, List<String> puzzles) {
}
