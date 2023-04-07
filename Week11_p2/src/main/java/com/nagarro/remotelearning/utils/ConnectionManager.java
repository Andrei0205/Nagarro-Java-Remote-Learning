package com.nagarro.remotelearning.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String CONNECTION_URL = "jdbc:postgresql://localhost:5432/Week11_p2";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public Connection getMyConnection() {
        try {
            return DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
