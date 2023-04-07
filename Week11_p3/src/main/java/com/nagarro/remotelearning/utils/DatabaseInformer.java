package com.nagarro.remotelearning.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseInformer {
    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String[] TABLE_TYPES = {"TABLE"};
    ConnectionManager connectionManager = new ConnectionManager();

    public String getDatabaseName() {
        try (
                Connection connection = connectionManager.getMyConnection();
        ) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();

            ResultSet names = databaseMetaData.getCatalogs();
            String databaseName = "";
            while (names.next()) {
                databaseName = names.getString(1);
            }
            return databaseName;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getDatabaseTables() {

        try (
                Connection connection = connectionManager.getMyConnection();
        ) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet tables = databaseMetaData.getTables(null, null, null, TABLE_TYPES);
            List<String> tablesName = new ArrayList<>();
            while (tables.next()) {
                tablesName.add(tables.getString(TABLE_NAME));
            }
            return tablesName;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getDatabaseColumns() {
        try (
                Connection connection = connectionManager.getMyConnection();
        ) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet tables = databaseMetaData.getTables(null, null, null, TABLE_TYPES);
            List<String> columnsName = new ArrayList<>();
            while (tables.next()) {
                String tableName = tables.getString(TABLE_NAME);
                ResultSet columns = databaseMetaData.getColumns(null, null, tableName, null);
                while (columns.next()) {
                    columnsName.add(columns.getString(COLUMN_NAME));
                }
            }
            return columnsName;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
