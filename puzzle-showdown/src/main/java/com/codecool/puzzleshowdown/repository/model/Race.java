package com.codecool.puzzleshowdown.repository.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "races")
public class Race {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @ManyToMany
    private List<User> players;
    @OneToOne
    private User winner;
    private LocalDate date;
    @ManyToMany
    private List<Puzzle> puzzles;

    public Race() {
    }

    public Race(List<User> players, User winner, LocalDate date, List<Puzzle> puzzles) {
        this.players = players;
        this.winner = winner;
        this.date = date;
        this.puzzles = puzzles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<User> getPlayers() {
        return players;
    }

    public void setPlayers(List<User> players) {
        this.players = players;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Puzzle> getPuzzles() {
        return puzzles;
    }

    public void setPuzzles(List<Puzzle> puzzles) {
        this.puzzles = puzzles;
    }
}
