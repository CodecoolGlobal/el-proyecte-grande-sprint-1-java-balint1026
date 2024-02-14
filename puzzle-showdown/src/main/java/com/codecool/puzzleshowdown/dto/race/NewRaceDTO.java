package com.codecool.puzzleshowdown.dto.race;

import java.util.List;

public record NewRaceDTO(List<Long> users, List<String> puzzles) {
}
