package com.nagarro.remotelearning.service;

import com.nagarro.remotelearning.annotations.Column;
import com.nagarro.remotelearning.annotations.Join;
import com.nagarro.remotelearning.annotations.Table;

import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DatabasePersistence {
    private final ConnectionManager connectionManager = new ConnectionManager();

    public void createTable(Class<?> modelClass) {
        try (Connection connection = connectionManager.getMyConnection();
             Statement statement = connection.createStatement()
        ) {
            String createCommand;
            if (getSQLCreateCommand(modelClass) != null) {
                createCommand = getSQLCreateCommand(modelClass);
            } else {
                throw new NoSuchElementException("Class not found or not contain any table definition");
            }
            statement.executeUpdate(createCommand);
        } catch (SQLException | NoSuchElementException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void addEntity(Object entity) {
        String insertCommand = getSQLInsertCommand(entity);
        if (insertCommand != null) {
            try (Connection connection = connectionManager.getMyConnection();
                 Statement statement = connection.createStatement()
            ) {
                statement.executeUpdate(insertCommand);
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

    public void selectAll(Class<?> modelClass) {
        //WORKS ONLY FOR SIMPLE SELECT

        //SELECT * FROM student
        //    JOIN address
        //    ON student.address.id = student.id;

        //todo reformat with methods

        String tableName;
        List<String> columnsNames;
        List<String> columnsTypes;
        List<String> joinColumnsNames;
        String selectCommand;

        try {
            tableName = getTableName(modelClass);
            columnsNames = getColumnsNames(modelClass); // without join column
            columnsTypes = getColumnsTypes(modelClass); // without join column
        } catch (NoSuchElementException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        List<String> joinTableNames = getJoinTableNames(modelClass);
        if (joinTableNames.isEmpty()) {
            selectCommand = getSQLSelectCommand(tableName);
        } else {
            List<String> referenceColumnName = getReferenceColumnNames(modelClass);
            List<String> joinMatchingColumnName = getNamesForJoiningColumns(modelClass);
            joinColumnsNames = getJoinColumnNames(modelClass);
            selectCommand = getSQLSelectCommand(tableName, joinTableNames, referenceColumnName, joinMatchingColumnName);
        }

        try (Connection connection = connectionManager.getMyConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectCommand);
            Map<Integer, List<String>> results = formatResultSet(columnsNames, columnsTypes, resultSet);
            for (Map.Entry mp : results.entrySet()) {               // ONLY FOR TESTING
                System.out.println(mp.getValue().toString());
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private Map<Integer, List<String>> formatResultSet(List<String> columnsNames, List<String> columnsTypes, ResultSet resultSet) {
        Map<Integer, List<String>> results = new HashMap<>();
        int mapIndex = 0;
        try {
            while (resultSet.next()) {
                List<String> rowResults = new ArrayList<>();
                for (int index = 0; index < columnsNames.size(); index++) {
                    if (columnsTypes.get(index).contains("String")) {
                        rowResults.add(resultSet.getString(columnsNames.get(index)));
                    }
                    if (columnsTypes.get(index).contains("int")) {
                        rowResults.add(String.valueOf(resultSet.getInt(columnsNames.get(index))));
                    }
                }
                results.put(mapIndex, rowResults);
                mapIndex++;
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return results;
    }

    private String getSQLSelectCommand(String tableName, List<String> joinTableNames,
                                       List<String> referenceColumnNames, List<String> joinMatchingColumnNames) {
        StringBuilder selectCommand = new StringBuilder();
        selectCommand.append("SELECT * FROM ").append(tableName);
        for (int index = 0; index < joinTableNames.size(); index++) {
            selectCommand.append("\n").append("JOIN ");
            selectCommand.append(joinTableNames.get(index));
            selectCommand.append("\n").append("ON ");
            selectCommand.append(tableName).append(".").append(referenceColumnNames.get(index)).append(" = ");
            selectCommand.append(joinTableNames.get(index)).append(".").append(joinMatchingColumnNames.get(index));
        }
        selectCommand.append(";");
        return selectCommand.toString();
    }

    private String getSQLSelectCommand(String tableName) {
        StringBuilder selectCommand = new StringBuilder();
        selectCommand.append("SELECT * FROM ").append(tableName).append(";");
        return selectCommand.toString();
    }

    private String getSQLCreateCommand(Class<?> modelClass) {
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

    private String getSQLInsertCommand(Object entity) {
        Class<?> entityClass = entity.getClass();
        String tableName;
        List<String> columns;
        List<String> values;
        try {
            tableName = getTableName(entityClass);
            columns = getColumnsNames(entityClass);
        } catch (NoSuchElementException e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
        values = getColumnsValues(entity);
        return buildSQLInsertCommand(tableName, columns, values);

    }

    private String buildSQLInsertCommand(String tableName, List<String> columns, List<String> values) {
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



    private List<String> getColumnsTypes(Class<?> modelClass) {
        List<String> columnTypes = new ArrayList<>();
        for (Field field : modelClass.getDeclaredFields()) {
            Column joinAnnotation = field.getAnnotation(Column.class);
            if (joinAnnotation != null && !field.isAnnotationPresent(Join.class)) {
                field.setAccessible(true);
                Type fieldType = field.getType();
                columnTypes.add(fieldType.getTypeName());
            }
        }
        if (columnTypes.isEmpty()) {
            throw new NoSuchElementException("Table has no column defined");
        }
        return columnTypes;
    }

    private List<String> getNamesForJoiningColumns(Class<?> modelClass) {
        List<String> joinMatchingColumnNames = new ArrayList<>();
        for (Field field : modelClass.getDeclaredFields()) {
            Join joinAnnotation = field.getAnnotation(Join.class);
            if (joinAnnotation != null) {
                joinMatchingColumnNames.add(joinAnnotation.joinByColumn());
            }
        }
        return joinMatchingColumnNames;
    }

    private List<String> getReferenceColumnNames(Class<?> modelClass) {
        List<String> referenceColumnNames = new ArrayList<>();
        for (Field field : modelClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class) &&
                    field.isAnnotationPresent(Join.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                referenceColumnNames.add(columnAnnotation.name());

            }
        }
        return referenceColumnNames;
    }

    private List<String> getJoinTableNames(Class<?> modelClass) {
        List<String> joinTablesNames = new ArrayList<>();
        for (Field field : modelClass.getDeclaredFields()) {
            Join joinAnnotation = field.getAnnotation(Join.class);
            if (joinAnnotation != null) {
                joinTablesNames.add(joinAnnotation.tableToJoin());
            }
        }
        return joinTablesNames;
    }

    private List<String> getColumnsValues(Object entity) {
        Class<?> entityClass = entity.getClass();
        List<String> values = new ArrayList<>();
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)
                    && !field.isAnnotationPresent(Join.class)) {
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
    /**
     @return return a List with name of columns that have only @Column annotation
     @throws NoSuchElementException if table has no column defined
     */
    private List<String> getColumnsNames(Class<?> modelClass) {
        List<String> columnNames = new ArrayList<>();
        for (Field field : modelClass.getDeclaredFields()) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation != null && !field.isAnnotationPresent(Join.class)) {
                columnNames.add(columnAnnotation.name());
            }
        }
        if (columnNames.isEmpty()) {
            throw new NoSuchElementException("Table has no column defined");
        }
        return columnNames;
    }

    private List<String> getJoinColumnNames(Class<?> modelClass) {
        List<String> columnNames = new ArrayList<>();
        for (Field field : modelClass.getDeclaredFields()) {
            Join joinAnnotation = field.getAnnotation(Join.class);
            if (joinAnnotation != null) {
                field.setAccessible(true);
                Type fieldType = field.getType();
                try {
                    Class<?> joinClass = Class.forName(fieldType.getTypeName());
                    columnNames = getColumnsNames(joinClass);
                } catch (ClassNotFoundException e) {
                    System.out.println("Class not found");
                }
            }
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