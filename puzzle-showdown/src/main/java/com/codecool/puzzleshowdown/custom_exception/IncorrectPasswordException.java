package com.codecool.puzzleshowdown.custom_exception;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException() {
        super("Invalid password!");
    }
}
