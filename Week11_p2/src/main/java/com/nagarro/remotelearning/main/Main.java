package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.DatabaseOperator;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseOperator databaseOperator = new DatabaseOperator();
        databaseOperator.executeOperation(1000.0, "Salary on April", 2);
    }
}