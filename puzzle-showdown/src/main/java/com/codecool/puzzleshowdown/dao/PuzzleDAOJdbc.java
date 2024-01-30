package com.codecool.puzzleshowdown.dao;

import com.codecool.puzzleshowdown.dao.model.ConnectionDB;
import com.codecool.puzzleshowdown.dao.model.Puzzle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class PuzzleDAOJdbc implements PuzzleDAO {

    private final int PUZZLE_SIZE = 5000;
    @Override
    public Puzzle getPuzzle() {
        Puzzle puzzle = null;
        try{
            Connection con = ConnectionDB.getConnection();
            PreparedStatement stmt = con.prepareStatement("select * from puzzles offset ? limit 1;");
            Random random = new Random();
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
    public void giveUpVote(String puzzleId) {

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
