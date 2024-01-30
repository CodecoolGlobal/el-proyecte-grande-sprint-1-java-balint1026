package com.codecool.puzzleshowdown.dao.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    public static final String DB_USERNAME = System.getenv("DB_USERNAME");
    public static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
    public static final String DB_URL = System.getenv("DB_URL");
    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            return conn;

        } catch (SQLException e) {
            System.out.println(e + " error");
        }

        return conn;
    }
}
