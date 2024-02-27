package com.codecool.puzzleshowdown.dto.user;

public record UserLoginResponseDTO(long userId, String username, String jwt, String image) {
}
