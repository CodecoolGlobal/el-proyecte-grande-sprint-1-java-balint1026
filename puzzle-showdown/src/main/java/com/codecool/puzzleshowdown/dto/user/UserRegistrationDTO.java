package com.codecool.puzzleshowdown.dto.user;

public record UserRegistrationDTO(
        String firstName,
        String lastName,
        String userName,
        String email,
        String password
) {
}
