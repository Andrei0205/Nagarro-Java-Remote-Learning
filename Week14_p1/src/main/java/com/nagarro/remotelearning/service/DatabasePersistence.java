package com.nagarro.remotelearning.service;

import com.nagarro.remotelearning.annotations.Column;
import com.nagarro.remotelearning.annotations.Join;
import com.nagarro.remotelearning.annotations.Table;

import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DatabasePersistence {
    private ConnectionManager connectionManager = new ConnectionManager();

    public void createTable(String modelClassName) {
        try (Connection connection = connectionManager.getMyConnection();
             Statement statement = connection.createStatement();
        ) {
            String createCommand;
            if (getSQLCreateCommandString(modelClassName) != null) {
                createCommand = getSQLCreateCommandString(modelClassName);
            } else {
                throw new NoSuchElementException("Class not found or not contain any table definition");
            }
            statement.executeUpdate(createCommand);
        } catch (SQLException | NoSuchElementException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void addEntity(Object entity) {
        Class<?> entityClass = entity.getClass();
        String tableName;
        List<String> columns;
        List<String> values;
        try {
            tableName = getTableName(entityClass);
            columns = getColumnsNames(entityClass);
        } catch (NoSuchElementException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        values = getColumnsValues(entity);
        try (Connection connection = connectionManager.getMyConnection();
             Statement statement = connection.createStatement();
        ) {
            String insertCommand = getSQLInsertCommand(tableName, columns, values);
            statement.executeUpdate(insertCommand);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
    public void selectAll(Class<?> modelClass){
//        SELECT student.name, employee.DepartmentID, address.street,address.city
//        FROM student
//        Join address
//        WHERE student.address_id = address.id;

        String tableName;
        List<String> columns;
        try {
            tableName = getTableName(modelClass);
            columns = getColumnsNames(modelClass);
        } catch (NoSuchElementException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
    }


    private String getSQLCreateCommandString(String modelClassName) {
        //todo return check
        Class<?> modelClass;
        try {
            modelClass = Class.forName(modelClassName);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
            return null;
        }
        String tableName;
        List<String> columnDefinitions;
        try {
            tableName = getTableName(modelClass);
            columnDefinitions = getColumnsDefinitions(modelClass);
        } catch (NoSuchElementException e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
        return buildSQLCreateCommand(tableName, columnDefinitions);
    }

    private String getSQLInsertCommand(String tableName, List<String> columns, List<String> values) {
        StringBuilder insertCommand = new StringBuilder();
        insertCommand.append("INSERT INTO ").append(tableName).append(" (");
        for (String column : columns) {
            insertCommand.append(column).append(",");
        }
        insertCommand.deleteCharAt(insertCommand.length() - 1);
        insertCommand.append(" )").append("\n ");
        insertCommand.append("VALUES ").append("(");
        for (String value : values) {
            insertCommand.append("'").append(value).append("'").append(",");
        }
        insertCommand.deleteCharAt(insertCommand.length() - 1);
        insertCommand.append(" )").append(";");
        return insertCommand.toString();
    }

    private List<String> getColumnsValues(Object entity) {
        Class<?> entityClass = entity.getClass();
        List<String> values = new ArrayList<>();
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                try {
                    Object value = field.get(entity);
                    values.add(value.toString());
                } catch (IllegalAccessException e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }
        return values;
    }

    private String buildSQLCreateCommand(String tableName, List<String> columnDefinitions) {
        StringBuilder createCommand = new StringBuilder();
        createCommand.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (");
        for (String columnDef : columnDefinitions) {
            createCommand.append("\n ").append(columnDef);
        }
        createCommand.deleteCharAt(createCommand.length() - 1);
        createCommand.append(");");
        return createCommand.toString();
    }

    private List<String> getColumnsDefinitions(Class<?> modelClass) {
        List<String> columnDefs = new ArrayList<>();
        for (Field field : modelClass.getDeclaredFields()) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                StringBuilder columnDefinition = new StringBuilder();
                columnDefinition.append(columnAnnotation.name()).append(" ");
                columnDefinition.append(columnAnnotation.type()).append(" ");
                columnDefinition.append(getConstraintsFromColumnAnnotation(columnAnnotation));
                columnDefinition.append(",");
                columnDefs.add(columnDefinition.toString());
            }
        }
        if (columnDefs.isEmpty()) {
            throw new NoSuchElementException("Table has no column defined");
        }
        return columnDefs;
    }

    private List<String> getColumnsNames(Class<?> modelClass) {
        List<String> columnNames = new ArrayList<>();
        for (Field field : modelClass.getDeclaredFields()) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                columnNames.add(columnAnnotation.name());
            }
        }
        if (columnNames.isEmpty()) {
            throw new NoSuchElementException("Table has no column defined");
        }
        return columnNames;
    }
    private List<String> getReferenceColumnNames(Class<?> modelClass) {
        List<String> columnNames = new ArrayList<>();
        for (Field field : modelClass.getDeclaredFields()) {
            Join joinAnnotation = field.getAnnotation(Join.class);
            if (joinAnnotation != null) {
                columnNames.add(joinAnnotation.tableToJoin());
            }
        }
        if (columnNames.isEmpty()) {
            throw new NoSuchElementException("Table has no column defined");
        }
        return columnNames;
    }


    private String getTableName(Class<?> modelClass) {
        Table dbTable = modelClass.getAnnotation(Table.class);
        if (dbTable == null) {
            throw new NoSuchElementException("Your model class does not contain any table");
        }
        return dbTable.name();
    }

    private String getConstraintsFromColumnAnnotation(Column annotation) {
        StringBuilder constraints = new StringBuilder();
        if (!annotation.allowNull()) {
            constraints.append("NOT NULL ");
        }
        if (annotation.primaryKey()) {
            constraints.append("PRIMARY KEY ");
        }
        if (annotation.unique()) {
            constraints.append("UNIQUE ");
        }
        if (constraints.length() == 0) {
            return "";
        }
        return constraints.toString();
    }
}