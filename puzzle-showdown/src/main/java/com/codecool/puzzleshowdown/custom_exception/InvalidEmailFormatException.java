package com.codecool.puzzleshowdown.custom_exception;

public class InvalidEmailFormatException extends RuntimeException{
    public InvalidEmailFormatException() {
        super("Invalid email format!");
    }
}
