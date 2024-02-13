package com.codecool.puzzleshowdown.custom_exception;

public class AlreadyExistingUserException extends RuntimeException{
    public AlreadyExistingUserException(String email) {
        super(String.format("User with email '%s' is already existing!", email));
    }
}
