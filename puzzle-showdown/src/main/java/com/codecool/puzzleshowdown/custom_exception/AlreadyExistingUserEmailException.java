package com.codecool.puzzleshowdown.custom_exception;

public class AlreadyExistingUserEmailException extends RuntimeException{
    public AlreadyExistingUserEmailException(String email) {
        super(String.format("User with email '%s' is already existing!", email));
    }
}
