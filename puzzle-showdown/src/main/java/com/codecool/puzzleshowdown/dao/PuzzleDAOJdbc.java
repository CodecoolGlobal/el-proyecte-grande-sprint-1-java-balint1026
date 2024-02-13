package com.codecool.puzzleshowdown.dao;

import com.codecool.puzzleshowdown.database.ConnectionDB;
import com.codecool.puzzleshowdown.dao.model.Puzzle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class PuzzleDAOJdbc implements PuzzleDAO {

    @Override
    public Puzzle getRandomPuzzle() {
        Puzzle puzzle = null;
        try{
            Connection con = ConnectionDB.getConnection();
            PreparedStatement stmt = con.prepareStatement("select * from puzzles offset ? limit 1;");
            Random random = new Random();

            int PUZZLE_SIZE = 2653;
            stmt.setInt(1, random.nextInt(PUZZLE_SIZE));
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
                puzzle = setResult(rs);
            con.close();

        }catch (SQLException e){
            System.out.println("Error: " + e);
        }
        return puzzle;
    }

    @Override
    public void updatePopularity(String puzzleId, int vote) {
        try{
            Connection con = ConnectionDB.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                        "update puzzles" +
                            " set popularity = popularity + ?" +
                            " where puzzleid = ?;"
            );
            stmt.setInt(1, vote);
            stmt.setString(2, puzzleId);
            stmt.executeQuery();
            con.close();
        }catch (SQLException e){
            System.out.println("Error: " + e);
        }
    }

    @Override
    public Puzzle getPuzzle(String puzzleId) {
        Puzzle puzzle = null;
        try{
            Connection con = ConnectionDB.getConnection();
            PreparedStatement stmt = con.prepareStatement("select * from puzzles where puzzleid = ?;");
            stmt.setString(1, puzzleId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
                puzzle = setResult(rs);
            con.close();

        }catch (SQLException e){
            System.out.println("Error: " + e);
        }
        return puzzle;
    }

    private Puzzle setResult(ResultSet rs) throws SQLException {
        return new Puzzle(rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getInt(5),
                rs.getInt(6),
                rs.getInt(7),
                rs.getString(8),
                rs.getString(9)
        );
    }
}
