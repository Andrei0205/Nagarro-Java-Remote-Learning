package com.nagarro.remotelearning.utils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQuery {
    ConnectionManager connectionManager = new ConnectionManager();

    public List<User> getAllClients() {
        final String selectAllQuery = "SELECT * FROM Clients;";
        try (Connection connection = connectionManager.getMyConnection();
             Statement statement = connection.createStatement();
        ) {
            List<User> userList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(selectAllQuery);
            return constructUsersList(userList, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public BigDecimal getBalanceForUser(String username) {
        final String selectBalanceQuery = "SELECT balance FROM clients WHERE username = ?;";

        try (Connection connection = connectionManager.getMyConnection();
             PreparedStatement statement = connection.prepareStatement(selectBalanceQuery);
        ) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            BigDecimal balanceForSelectedUser = null;
            while (resultSet.next()) {
                balanceForSelectedUser = resultSet.getBigDecimal(1);
            }
            resultSet.close();
            return balanceForSelectedUser;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getBusinessClients(BigDecimal target) {
        final String selectBusinessClientsQuery = "SELECT * FROM Clients WHERE balance > ?;";
        try (Connection connection = connectionManager.getMyConnection();
             PreparedStatement statement = connection.prepareStatement(selectBusinessClientsQuery);
        ) {
            statement.setBigDecimal(1, target);
            ResultSet resultSet = statement.executeQuery();
            List<User> userList = new ArrayList<>();
            return constructUsersList(userList, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<User> constructUsersList(List<User> userList, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String username = resultSet.getString(3);
            BigDecimal balance = resultSet.getBigDecimal(4);
            userList.add(new User(id, name, username, balance));
        }
        resultSet.close();
        return userList;
    }


}
