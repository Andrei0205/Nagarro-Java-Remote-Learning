package com.nagarro.remotelearning.service;

import com.nagarro.remotelearning.annotations.Column;
import com.nagarro.remotelearning.annotations.Join;
import com.nagarro.remotelearning.annotations.Table;
import com.nagarro.remotelearning.exceptions.ColumnException;
import com.nagarro.remotelearning.exceptions.DatabasePersistenceException;
import com.nagarro.remotelearning.exceptions.JoinColumnException;
import javafx.util.Pair;


import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatabasePersistence {
    private final ConnectionManager connectionManager = new ConnectionManager();

    public void createTable(Class<?> modelClass) {
        String createCommand = getSQLCreateCommand(modelClass);
        executeStatement(createCommand);
    }


    public void addEntity(Object entity) {
        if (isJoinAnnotationPresent(entity)) {
            Object referenceEntity = getReferenceEntity(entity);
            String insertReferenceEntityCommand = getSQLInsertCommand(referenceEntity);
            String insertEntityCommand = getSQLInsertCommand(entity);
            executeTransactions(insertEntityCommand, insertReferenceEntityCommand);
        } else {
            String insertEntityCommand = getSQLInsertCommand(entity);
            executeStatement(insertEntityCommand);
        }
    }


    public void selectAll(Class<?> modelClass) throws ClassNotFoundException {
        String selectCommand = getSQLSelectCommand(modelClass);
        List<Pair<String, String>> columnTypesAndNames = getColumnsTypesAndNames(modelClass);
        if (isJoinAnnotationPresent(modelClass)) {
            Class<?> joinClass = Class.forName(getReferenceClass(modelClass));
            List<Pair<String, String>> joinColumnTypesAndNames = getColumnsTypesAndNames(joinClass);
            columnTypesAndNames.addAll(joinColumnTypesAndNames);
        }

        try (Connection connection = connectionManager.getMyConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectCommand);
            Map<Integer, List<String>> results = formatResultSet(columnTypesAndNames, resultSet);
            results.forEach((key, value) -> System.out.println(key + " : " + value));
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private boolean isJoinAnnotationPresent(Object entity) {
        Class<?> entityClass = entity.getClass();
        Field[] fields = entityClass.getDeclaredFields();
        return Stream.of(fields).anyMatch(
                field -> field.isAnnotationPresent(Join.class));
    }

    private boolean isJoinAnnotationPresent(Class<?> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        return Stream.of(fields).anyMatch(
                field -> field.isAnnotationPresent(Join.class));
    }

    private void executeStatement(String createCommand) {
        try (Connection connection = connectionManager.getMyConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(createCommand);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private void executeTransactions(String insertEntityCommand, String insertReferenceEntityCommand) {
        Connection connection = connectionManager.getMyConnection();
        try (
                Statement statement = connection.createStatement()
        ) {
            connection.setAutoCommit(false);
            statement.executeUpdate(insertReferenceEntityCommand);

            statement.executeUpdate(insertEntityCommand);
            connection.commit();
        } catch (SQLException e) {
            try {
                System.err.print("Transaction is being rolled back");
                connection.rollback();
                throw new DatabasePersistenceException(e.getLocalizedMessage());
            } catch (SQLException excep) {
                throw new DatabasePersistenceException(excep.getLocalizedMessage());
            }
        }
    }

    private Map<Integer, List<String>> formatResultSet(List<Pair<String, String>> columnTypesAndNames, ResultSet resultSet) throws SQLException {
        Map<Integer, List<String>> results = new HashMap<>();
        int mapIndex = 0;
        while (resultSet.next()) {
            List<String> rowResults = new ArrayList<>();
            for (int index = 0; index < columnTypesAndNames.size(); index++) {
                if (columnTypesAndNames.get(index).getKey().contains("int")) {
                    rowResults.add(String.valueOf(resultSet.getInt(index + 1)));
                } else {
                    rowResults.add(resultSet.getString(index + 1));
                }
            }
            results.put(mapIndex, rowResults);
            mapIndex++;
        }
        return results;
    }

    private String buildSQLSelectCommand(String tableName, String joinTableName,
                                         List<Pair<String, String>> columnTypesAndNames,
                                         List<Pair<String, String>> joinColumnTypesAndNames,
                                         Pair<String, String> referenceColumnAndHisMatching) {
        StringBuilder selectCommand = new StringBuilder();
        selectCommand.append("SELECT ");
        for (Pair<String, String> column : columnTypesAndNames) {
            selectCommand.append(String.join(".", tableName, column.getValue()));
            selectCommand.append(",");
        }
        for (Pair<String, String> joinColumn : joinColumnTypesAndNames) {
            selectCommand.append(String.join(".", joinTableName, joinColumn.getValue()));
            selectCommand.append(",");
        }
        selectCommand.deleteCharAt(selectCommand.length() - 1);
        selectCommand.append("\n").append("FROM ").append(tableName);
        selectCommand.append("\n").append("JOIN ").append(joinTableName);
        selectCommand.append("\n").append("ON ");
        selectCommand.append(tableName).append(".").append(referenceColumnAndHisMatching.getKey()).append(" = ");
        selectCommand.append(joinTableName).append(".").append(referenceColumnAndHisMatching.getValue());
        selectCommand.append(";");
        return selectCommand.toString();
    }

    private String getSQLSelectCommand(Class<?> modelClass) throws ClassNotFoundException {
        String tableName = getTableName(modelClass);
        if (isJoinAnnotationPresent(modelClass)) {
            List<Pair<String, String>> columnTypesAndNames = getColumnsTypesAndNames(modelClass);
            Class<?> joinClass = Class.forName(getReferenceClass(modelClass));
            List<Pair<String, String>> joinColumnTypesAndNames = getColumnsTypesAndNames(joinClass);
            Pair<String, String> referenceColumnAndHisMatching = getReferenceColumnAndHisMatching(modelClass);
            String joinTableName = getJoinTableName(modelClass);
            return buildSQLSelectCommand(tableName, joinTableName, columnTypesAndNames, joinColumnTypesAndNames, referenceColumnAndHisMatching);
        } else {
            StringBuilder selectCommand = new StringBuilder();
            selectCommand.append("SELECT * FROM ").append(tableName).append(";");
            return selectCommand.toString();
        }
    }

    private Pair<String, String> getReferenceColumnAndHisMatching(Class<?> modelClass) {
        return Stream.of(modelClass.getDeclaredFields()).filter(
                field -> field.isAnnotationPresent(Join.class)
        ).map(field -> {
                    Join joinAnnotation = field.getAnnotation(Join.class);
                    String columnName = String.join("_", joinAnnotation.tableToJoin(), joinAnnotation.joinByColumn());
                    String matchingColumn = joinAnnotation.joinByColumn();
                    return new Pair<>(columnName, matchingColumn);
                }
        ).findFirst().orElseThrow(() -> new RuntimeException("No field with Join annotation found"));
    }

    private String getSQLCreateCommand(Class<?> modelClass) {
        String tableName = getTableName(modelClass);
        List<String> columnDefinitions = getColumnsDefinitions(modelClass);
        if (isJoinAnnotationPresent(modelClass)) {
            columnDefinitions.addAll(getJoinColumnDefinition(modelClass));
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
        String tableName = getTableName(entityClass);
        List<Pair<String, String>> columnsAndValues = getColumnNamesAndValues(entity);
        if (isJoinAnnotationPresent(entity)) {
            columnsAndValues.add(new Pair<>(getReferenceColumnName(entityClass),
                    getReferenceValue(entity)));
        }
        return buildSQLInsertCommand(tableName, columnsAndValues);
    }

    private String buildSQLInsertCommand(String tableName, List<Pair<String, String>> columnsAndValues) {
        StringBuilder insertCommand = new StringBuilder();
        insertCommand.append("INSERT INTO ").append(tableName).append(" (");
        for (Pair<String, String> column : columnsAndValues) {
            insertCommand.append(column.getKey());
            insertCommand.append(",");
        }
        insertCommand.deleteCharAt(insertCommand.length() - 1);
        insertCommand.append(" )").append("\n ");
        insertCommand.append("VALUES ").append("(");
        for (Pair<String, String> value : columnsAndValues) {
            insertCommand.append("'").append(value.getValue()).append("'").append(",");
        }
        insertCommand.deleteCharAt(insertCommand.length() - 1);
        insertCommand.append(" )").append(";");
        return insertCommand.toString();
    }

    private List<Pair<String, String>> getColumnNamesAndValues(Object entity) {
        Class<?> entityClass = entity.getClass();
        return Stream.of(entityClass.getDeclaredFields()).filter(
                field -> field.isAnnotationPresent(Column.class)
        ).map(field -> {
                    field.setAccessible(true);
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    String columnName = columnAnnotation.name();
                    String columnValue;
                    try {
                        columnValue = field.get(entity).toString();
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    return new Pair<>(columnName, columnValue);
                }
        ).collect(Collectors.toList());
    }

    private String getReferenceValue(Object entity) {
        Class<?> entityClass = entity.getClass();
        String columnName = Stream.of(entityClass.getDeclaredFields()).filter(
                field -> field.isAnnotationPresent(Join.class)
        ).map(field -> {
            Join joinAnnotation = field.getAnnotation(Join.class);
            return joinAnnotation.joinByColumn();
        }).findFirst().orElseThrow(() -> new RuntimeException("No field with Join annotation found"));
        return getValueForSpecificColumn(getReferenceEntity(entity), columnName);
    }

    private String getValueForSpecificColumn(Object entity, String columnName) {
        Class<?> entityClass = entity.getClass();
        return Stream.of(entityClass.getDeclaredFields()).filter(
                field -> {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    return columnAnnotation.name().equals(columnName);
                }
        ).map(field -> {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(entity);
                        return value.toString();
                    } catch (IllegalAccessException e) {
                        throw new DatabasePersistenceException(e.getLocalizedMessage());
                    }
                }
        ).findFirst().orElseThrow(() -> new ColumnException("Something went wrong"));
    }

    /**
     * Return a List of Strings with declared types of fields with @Column annotation
     *
     * @return List
     * @throws NoSuchElementException if class has no field with @Column annotation
     */
    private List<String> getColumnsTypes(Class<?> modelClass) {
        List<String> columnTypes = new ArrayList<>();
        for (Field field : modelClass.getDeclaredFields()) { // with streams collect column and value
            //stream.filter.map(pair).collect
            Column columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                field.setAccessible(true);
                Type fieldType = field.getType();
                columnTypes.add(fieldType.getTypeName());
            }
        }
        if (columnTypes.isEmpty()) {
            throw new ColumnException("Table has no column defined");
        }
        return columnTypes;
    }

    /**
     * Return a List of Strings with columns that match with reference fields declared in current class,
     * columns that you need for join condition
     *
     * @return List
     */
    private List<String> getMatchingColumnsNames(Class<?> modelClass) {
        List<String> joinMatchingColumnNames = new ArrayList<>();
        for (Field field : modelClass.getDeclaredFields()) {
            Join joinAnnotation = field.getAnnotation(Join.class);
            if (joinAnnotation != null) {
                joinMatchingColumnNames.add(joinAnnotation.joinByColumn());
            }
        }
        return joinMatchingColumnNames;
    }

    /**
     * Return a list of Strings with all fields that have @Join annotation
     *
     * @return List
     */
    private String getReferenceColumnName(Class<?> modelClass) {
        return Stream.of(modelClass.getDeclaredFields()).filter(
                field -> field.isAnnotationPresent(Join.class)
        ).map(field -> {
            Join joinAnnotation = field.getAnnotation(Join.class);
            String columnName = String.join("_", joinAnnotation.tableToJoin(), joinAnnotation.joinByColumn());
            return columnName;
        }).findFirst().orElseThrow(() -> new RuntimeException("No field with Join annotation found"));

    }

    /**
     * Return the name for table to join
     *
     * @return List
     */
    private String getJoinTableName(Class<?> modelClass) {
        return Stream.of(modelClass.getDeclaredFields()).filter(
                field -> field.isAnnotationPresent(Join.class)
        ).map(
                field -> {
                    Join joinAnnotation = field.getAnnotation(Join.class);
                    return joinAnnotation.tableToJoin();
                }
        ).findFirst().orElseThrow(() -> new RuntimeException("No field with Join annotation found"));
    }

    /**
     * Return a list of Strings with values contained by every
     * field that have only @Column annotation
     *
     * @return List
     * @throws NoSuchElementException if table has no column defined
     */
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

    private Object getReferenceEntity(Object entity) {
        Class<?> entityClass = entity.getClass();
        Field[] fields = entityClass.getDeclaredFields();
        return Stream.of(fields).filter(
                field -> field.isAnnotationPresent(Join.class)
        ).map(field -> {
            field.setAccessible(true);
            try {
                return field.get(entity);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).findFirst().orElseThrow(() -> new DatabasePersistenceException("Something went wrong"));
    }

    private String getReferenceClass(Class<?> entityClass) {
        return Stream.of(entityClass.getDeclaredFields()).filter(
                field -> field.isAnnotationPresent(Join.class)
        ).map(field -> {
            field.setAccessible(true);
            return field.getType().getTypeName();
        }).findFirst().orElseThrow(() -> new JoinColumnException("No field with Join annotation"));
    }

    /**
     * Return a list with definitions for every field that have @Column annotation
     * Example of a column definition 'id INTEGER PRIMARY KEY NOT NULL,'
     *
     * @return List
     * @throws NoSuchElementException if table has no column defined
     */
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

    private List<String> getJoinColumnDefinition(Class<?> modelClass) {
        List<String> columnDefs = new ArrayList<>();
        for (Field field : modelClass.getDeclaredFields()) {
            Join joinAnnotation = field.getAnnotation(Join.class);
            if (joinAnnotation != null) {
                StringBuilder columnDefinition = new StringBuilder();
                columnDefinition.append(joinAnnotation.tableToJoin()).append("_");
                columnDefinition.append(joinAnnotation.joinByColumn()).append(" ");
                columnDefinition.append("INTEGER ");
                columnDefinition.append("NOT NULL");
                columnDefinition.append(",");
                columnDefs.add(columnDefinition.toString());
            }
        }
        return columnDefs;
    }

    /**
     * @return List with names of columns that have only @Column annotation
     * @throws NoSuchElementException if table has no column defined
     */
    private List<String> getColumnsNames(Class<?> modelClass) {
        List<String> columnNames = Stream.of(modelClass.getDeclaredFields()).filter(
                field -> field.isAnnotationPresent(Column.class)
        ).map(
                field -> {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    return columnAnnotation.name();
                }
        ).collect(Collectors.toList());
        if (columnNames.isEmpty()) {
            throw new NoSuchElementException("Table has no column defined");
        }
        return columnNames;
    }

    private List<Pair<String, String>> getColumnsTypesAndNames(Class<?> modelClass) {
        List<Pair<String, String>> columnNames = Stream.of(modelClass.getDeclaredFields()).filter(
                field -> field.isAnnotationPresent(Column.class)
        ).map(
                field -> {
                    field.setAccessible(true);
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    String fieldType = field.getType().getTypeName();
                    String columnName = columnAnnotation.name();
                    return new Pair<>(fieldType, columnName);
                }
        ).collect(Collectors.toList());
        if (columnNames.isEmpty()) {
            throw new NoSuchElementException("Table has no column defined");
        }
        return columnNames;
    }

    private List<String> getJoinColumnsNames(Class<?> modelClass) throws ClassNotFoundException {
        List<String> columnNames = new ArrayList<>();
        for (Field field : modelClass.getDeclaredFields()) {
            Join joinAnnotation = field.getAnnotation(Join.class);
            if (joinAnnotation != null) {
                field.setAccessible(true);
                Type fieldType = field.getType();
                Class<?> joinClass = Class.forName(fieldType.getTypeName());
                columnNames = getColumnsNames(joinClass);
            }
        }
        return columnNames;
    }

    private String getTableName(Class<?> modelClass) {
        Table dbTable = modelClass.getAnnotation(Table.class);
        if (dbTable == null) {
            throw new RuntimeException("Your model class does not contain any table");
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