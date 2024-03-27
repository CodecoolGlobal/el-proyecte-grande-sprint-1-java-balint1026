package com.codecool.puzzleshowdown.stateFul;

public class GameState {


    private boolean isRaceActive = true;

    private boolean isPending = true;

    public boolean isPending() {
        return isPending;
    }

    public boolean isRaceActive() {
        return isRaceActive;
    }

    public void setRaceActive(boolean raceActive) {
        isRaceActive = raceActive;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }
}
