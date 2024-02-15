package com.codecool.puzzleshowdown.dto.user;

import com.codecool.puzzleshowdown.repository.model.Puzzle;

import java.util.List;

public record UserDTO(long userId, String username, String password, List<Puzzle> solvedPuzzles) {
}
