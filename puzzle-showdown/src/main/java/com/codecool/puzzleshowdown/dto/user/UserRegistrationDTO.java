package com.codecool.puzzleshowdown.dto.user;

public record UserRegistrationDTO(
        String userName,
        String email,
        String password
) {
}
