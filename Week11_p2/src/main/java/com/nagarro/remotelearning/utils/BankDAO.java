package com.nagarro.remotelearning.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankDAO {
    public void executeOperation(Double amount, String description, int userId) throws SQLException {
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = connectionManager.getMyConnection();

        final String insertString = "INSERT INTO operations (amount, description, client_id) VALUES( ?, ?, ?)";
        final String updateBalance = "UPDATE clients SET balance = balance + ? WHERE id = ?";

        try (
                PreparedStatement updateBalanceStatement = connection.prepareStatement(updateBalance);
                PreparedStatement insertOperation = connection.prepareStatement(insertString)
        ) {
            connection.setAutoCommit(false);

            updateBalanceStatement.setDouble(1, amount);
            updateBalanceStatement.setInt(2, userId);
            updateBalanceStatement.executeUpdate();

            insertOperation.setDouble(1, amount);
            insertOperation.setString(2, description);
            insertOperation.setInt(3, userId);
            insertOperation.addBatch();
            insertOperation.executeBatch();

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    throw new RuntimeException(e);
                } catch (SQLException excep) {
                    throw new RuntimeException(excep);
                }
            }
        } finally {
            connection.close();
        }
    }
}
