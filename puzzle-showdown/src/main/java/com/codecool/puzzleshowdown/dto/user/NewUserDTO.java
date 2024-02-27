package com.codecool.puzzleshowdown.dto.user;

public record NewUserDTO(
        String username,
        String email,
        String password
) {
}
