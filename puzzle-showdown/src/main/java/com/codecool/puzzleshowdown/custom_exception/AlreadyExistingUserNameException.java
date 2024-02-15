package com.codecool.puzzleshowdown.custom_exception;

public class AlreadyExistingUserNameException extends RuntimeException{
    public AlreadyExistingUserNameException(String userName) {
        super(String.format("User with user name '%s' is already existing!", userName));
    }
}
