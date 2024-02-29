package com.codecool.puzzleshowdown.service;

import com.codecool.puzzleshowdown.repository.PuzzleRepository;
import com.codecool.puzzleshowdown.repository.UserRepository;
import com.codecool.puzzleshowdown.repository.model.Puzzle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PuzzleServiceTest {

    private PuzzleService puzzleService;
    private UserRepository userRepositoryMock = mock(UserRepository.class);
    private PuzzleRepository puzzleRepositoryMock = mock(PuzzleRepository.class);
    @BeforeEach
    void setup(){
        puzzleService = new PuzzleService(puzzleRepositoryMock, userRepositoryMock);
    }

    @Test
    void isValidStep_withValidMove_shouldReturnTheNextMove_wouldBe_a4() {
        //arrange
        Puzzle expectedPuzzle = new Puzzle();
        expectedPuzzle.setPuzzleid("#000");
        expectedPuzzle.setMoves("a1 a2 a3 a4 a5");
        Optional<Puzzle> puzzle = Optional.of(expectedPuzzle);

        when(puzzleRepositoryMock.findById("#000")).thenReturn(puzzle);

        //act
        String result = puzzleService.isValidStep("#000", "a3",2);
        String expected = "a4";
        //assert
        assertEquals(expected, result);
    }


    @Test
    void isValidStep_InvalidMove_shouldReturnNull() {
        //arrange
        Puzzle expectedPuzzle = new Puzzle();
        expectedPuzzle.setPuzzleid("#000");
        expectedPuzzle.setMoves("a1 a2 a3 a4 a5");
        Optional<Puzzle> puzzle = Optional.of(expectedPuzzle);

        when(puzzleRepositoryMock.findById("#000")).thenReturn(puzzle);

        //act
        String result = puzzleService.isValidStep("#000", "b25",2);
        //assert
        assertNull(result);
    }

    @Test
    void isValidStep_WalidLastStep_shouldReturnWinText() {
        //arrange
        Puzzle expectedPuzzle = new Puzzle();
        expectedPuzzle.setPuzzleid("#000");
        expectedPuzzle.setMoves("a1 a2 a3 a4 a5");
        Optional<Puzzle> puzzle = Optional.of(expectedPuzzle);

        when(puzzleRepositoryMock.findById("#000")).thenReturn(puzzle);

        //act
        String result = puzzleService.isValidStep("#000", "a5",4);
        String expected = "win";
        //assert
        assertEquals(expected, result);
    }

    @Test
    void getHint_ShouldReturnTheSecondHint_fromTheFirstPuzzle_ShouldReturn_a2() {
        //arrange
        Puzzle expectedPuzzle = new Puzzle();
        expectedPuzzle.setPuzzleid("#000");
        expectedPuzzle.setMoves("a1 a2 a3 a4 a5");
        Optional<Puzzle> puzzle = Optional.of(expectedPuzzle);

        when(puzzleRepositoryMock.findById("#000")).thenReturn(puzzle);

        String result = puzzleService.getHint("#000",1);
        String expected = "a2";

        assertEquals(expected, result);
    }
}