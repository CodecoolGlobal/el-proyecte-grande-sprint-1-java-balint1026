package com.codecool.puzzleshowdown.stateFul;

public class GameState {

    private boolean isPending = true;

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }
}
