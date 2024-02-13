package com.codecool.puzzleshowdown.dao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "puzzles")
public class Puzzle {
    @Id
    private String puzzleid;
    private String fen;
    private String moves;
    private int rating;
    private int ratingdeviation;
    private int popularity;
    private int nbplays;
    private String themes;
    private String gameurl;
    private String openingtags;

    public Puzzle() {

    }

    public void setOpeningtags(String openingtags) {
        this.openingtags = openingtags;
    }

    public String getOpeningtags() {
        return openingtags;
    }

    public void setPuzzleid(String puzzleId) {
        this.puzzleid = puzzleId;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    public void setMoves(String moves) {
        this.moves = moves;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setRatingdeviation(int ratingDeviation) {
        this.ratingdeviation = ratingDeviation;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void setNbplays(int nbPlays) {
        this.nbplays = nbPlays;
    }

    public void setThemes(String themes) {
        this.themes = themes;
    }

    public void setGameurl(String gameUrl) {
        this.gameurl = gameUrl;
    }

    public String getPuzzleid() {
        return puzzleid;
    }

    public String getFen() {
        return fen;
    }

    public String getMoves() {
        return moves;
    }

    public int getRating() {
        return rating;
    }

    public int getRatingdeviation() {
        return ratingdeviation;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getNbplays() {
        return nbplays;
    }

    public String getThemes() {
        return themes;
    }

    public String getGameurl() {
        return gameurl;
    }
}
