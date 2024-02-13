package com.codecool.puzzleshowdown.custom_exception;

public class NonExistingUserException extends RuntimeException{
    public NonExistingUserException(String authenticator) {
        super(String.format("User with '%s' is not exists!", authenticator));
    }
}
