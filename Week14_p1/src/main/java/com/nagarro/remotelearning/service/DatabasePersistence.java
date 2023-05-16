package com.nagarro.remotelearning.service;

import com.nagarro.remotelearning.annotations.Column;
import com.nagarro.remotelearning.annotations.Converter;
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
        List<?> results;
        try (Connection connection = connectionManager.getMyConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectCommand);
            results = formatResultSet(resultSet, modelClass);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | SQLException e) {
            throw new DatabasePersistenceException(e.getLocalizedMessage());
        }
        for (Object obj : results) { //ONLY FOR TESTING
            System.out.println(obj);
        }
    }

    private List<?> formatResultSet(ResultSet resultSet, Class<?> modelClass) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String convertMethodName = modelClass.getAnnotation(Converter.class).name();
        Class[] convertMethodArgument = {ResultSet.class};
        Method convertMethod = DatabaseTypeConverter.class.getMethod(convertMethodName, convertMethodArgument);
        Object databaseTypeConverter = DatabaseTypeConverter.class.newInstance();
        List<Object> objectsFromDatabase = new ArrayList<>();
        while (resultSet.next()) {
            try {
                objectsFromDatabase.add(convertMethod.invoke(databaseTypeConverter, resultSet));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new DatabasePersistenceException(e.getLocalizedMessage());
            }
        }
        return objectsFromDatabase;
    }

    private boolean isJoinAnnotationPresent(Object entity) {
        Class<?> entityClass = entity.getClass();
        return Stream.of(entityClass.getDeclaredFields()).anyMatch(
                field -> field.isAnnotationPresent(Join.class));
    }

    private boolean isJoinAnnotationPresent(Class<?> entityClass) {
        return Stream.of(entityClass.getDeclaredFields()).anyMatch(
                field -> field.isAnnotationPresent(Join.class));
    }

    private void executeStatement(String createCommand) {
        try (Connection connection = connectionManager.getMyConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(createCommand);
        } catch (SQLException e) {
            throw new DatabasePersistenceException(e.getLocalizedMessage());
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

    private String buildSQLSelectCommand(String tableName, String joinTableName,
                                         List<Pair<String, String>> columnTypesAndNames,
                                         List<Pair<String, String>> joinColumnTypesAndNames,
                                         Pair<String, String> referenceColumnAndHisMatching) {
        String selectCommand = "SELECT tables.columns FROM tableName " +
                "JOIN joinTableName " +
                "ON tableName.referenceColumn = joinTableName.matchingColumn";
        String columns = columnTypesAndNames.stream().map(pair -> tableName + "." + pair.getValue())
                .collect(Collectors.joining(" , "));
        String joinColumns = joinColumnTypesAndNames.stream().map(pair -> joinTableName + "." + pair.getValue()).
                collect(Collectors.joining(" , "));
        selectCommand = selectCommand.replace("tables.columns", String.join(" , ", columns, joinColumns));
        selectCommand = selectCommand.replace("tableName", tableName);
        selectCommand = selectCommand.replace("joinTableName", joinTableName);
        selectCommand = selectCommand.replace("referenceColumn", referenceColumnAndHisMatching.getKey());
        selectCommand = selectCommand.replace("matchingColumn", referenceColumnAndHisMatching.getValue());
        return selectCommand;
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
        ).findFirst().orElseThrow(() -> new JoinColumnException("No field with Join annotation found"));
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
        String createCommand = "CREATE TABLE IF NOT EXISTS tableName ( columnDefinitions );";
        createCommand = createCommand.replace("tableName", tableName);
        String columnDefinitionsString = String.join(" , ", columnDefinitions);
        createCommand = createCommand.replace("columnDefinitions", columnDefinitionsString);
        return createCommand;
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
        String insertCommand = "INSERT INTO tableName ( columns ) VALUES ( values );";
        insertCommand = insertCommand.replace("tableName", tableName);
        String columns = columnsAndValues.stream().map(Pair::getKey).collect(Collectors.joining(","));
        insertCommand = insertCommand.replace("columns", columns);
        String values = columnsAndValues.stream().map(
                pair -> "'" + pair.getValue() + "'").collect(Collectors.joining(","));
        insertCommand = insertCommand.replace("values", values);
        return insertCommand;
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
                        throw new DatabasePersistenceException(e.getLocalizedMessage());
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
        }).findFirst().orElseThrow(() -> new JoinColumnException("No field with Join annotation found"));
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
        }).findFirst().orElseThrow(() -> new JoinColumnException("No field with Join annotation found"));

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
        ).findFirst().orElseThrow(() -> new JoinColumnException("No field with Join annotation found"));
    }

    private Object getReferenceEntity(Object entity) {
        Class<?> entityClass = entity.getClass();
        return Stream.of(entityClass.getDeclaredFields()).filter(
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
     * Example of a column definition 'id INTEGER PRIMARY KEY NOT NULL'
     *
     * @return List
     * @throws NoSuchElementException if table has no column defined
     */
    private List<String> getColumnsDefinitions(Class<?> modelClass) {
        List<String> columnDefs = Stream.of(modelClass.getDeclaredFields()).filter(
                field -> field.isAnnotationPresent(Column.class)
        ).map(
                field -> {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    String columnDefinition = columnAnnotation.name() + " " + columnAnnotation.type() + " "
                            + getConstraintsFromColumnAnnotation(columnAnnotation);
                    return columnDefinition;
                }
        ).collect(Collectors.toList());
        if (columnDefs.isEmpty()) {
            throw new ColumnException("Table has no column defined");
        }
        return columnDefs;
    }

    private List<String> getJoinColumnDefinition(Class<?> modelClass) {
        return Stream.of(modelClass.getDeclaredFields()).filter(
                field -> field.isAnnotationPresent(Join.class)
        ).map(
                field -> {
                    Join joinAnnotation = field.getAnnotation(Join.class);
                    String columnDefinition =
                            String.join("_", joinAnnotation.tableToJoin(), joinAnnotation.joinByColumn())
                                    + "INTEGER" + "NOT NULL";
                    return columnDefinition;
                }
        ).collect(Collectors.toList());
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
            throw new ColumnException("Table has no column defined");
        }
        return columnNames;
    }

    private String getTableName(Class<?> modelClass) {
        Table dbTable = modelClass.getAnnotation(Table.class);
        if (dbTable == null) {
            throw new DatabasePersistenceException("Your model class does not contain any table");
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