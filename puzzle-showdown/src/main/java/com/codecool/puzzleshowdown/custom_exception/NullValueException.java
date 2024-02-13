package com.codecool.puzzleshowdown.custom_exception;

public class NullValueException extends RuntimeException{
    public NullValueException() {
        super("Can't leave fields empty!");
    }
}
