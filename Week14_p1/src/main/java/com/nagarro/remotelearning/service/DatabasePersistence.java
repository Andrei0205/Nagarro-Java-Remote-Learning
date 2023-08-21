package com.nagarro.remotelearning.service;

import com.nagarro.remotelearning.annotations.Column;
import com.nagarro.remotelearning.annotations.Join;
import com.nagarro.remotelearning.annotations.MyConverter;
import com.nagarro.remotelearning.annotations.Table;
import com.nagarro.remotelearning.exceptions.ColumnException;
import com.nagarro.remotelearning.exceptions.DatabasePersistenceException;
import com.nagarro.remotelearning.exceptions.JoinColumnException;
import com.nagarro.remotelearning.model.Student;
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
            Object joinEntity = getJoinEntity(entity);
            String insertJoinEntityCommand = getSQLInsertCommand(joinEntity);
            String insertEntityCommand = getSQLInsertCommand(entity);
            executeTransactions(insertEntityCommand, insertJoinEntityCommand);
        } else {
            String insertEntityCommand = getSQLInsertCommand(entity);
            executeStatement(insertEntityCommand);
        }
    }

    public void selectSpecificEntity(Class<?> modelClass, int id) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String selectCommand = getSQLSelectCommand(modelClass, id);
        try (Connection connection = connectionManager.getMyConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectCommand);
            while (resultSet.next()) {
                System.out.println(extractObjectFromResultRow(resultSet, modelClass));
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectAll(Class<?> modelClass) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String selectCommand = getSQLSelectAllCommand(modelClass);
        List<Object> results = new ArrayList<>();
        try (Connection connection = connectionManager.getMyConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectCommand);
            while (resultSet.next()) {
                results.add(extractObjectFromResultRow(resultSet, modelClass));
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        for (Object obj : results) { //ONLY FOR TESTING
            System.out.println(obj);
        }
    }

    public void update(Class<?> modelClass,int id, String fieldToUpdate, String value) {

    }

    private Object extractObjectFromResultRow(ResultSet resultSet, Class<?> modelClass) throws SQLException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return extractObjectFromResultRow(resultSet, modelClass, 1);
    }

    private Object extractObjectFromResultRow(ResultSet resultSet, Class<?> modelClass, Integer iterator) throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException {
        Object obj = modelClass.newInstance();
        List<Field> fields = getDBFields(modelClass.getDeclaredFields());
        for (Field field : fields) {
            Method setterMethod = getSetterMethod(modelClass, field);
            if (field.isAnnotationPresent(Join.class)) {
                Class<?> joinModelClass = Class.forName(field.getType().getTypeName());
                Object joinObject = extractObjectFromResultRow(resultSet, joinModelClass, iterator);
                setterMethod.invoke(obj, joinObject);
                iterator += getFieldCount(joinObject.getClass());
                continue;
            }
            Object value = resultSet.getObject(iterator);
            if (field.isAnnotationPresent(MyConverter.class)) {
                value = convertObject(value, field);
            }
            setterMethod.invoke(obj, value);
            iterator++;
        }
        return obj;
    }

    private int getFieldCount(Class<?> modelClass) {
        return getDBFields(modelClass.getDeclaredFields()).size();
    }

    private List<Field> getDBFields(Field[] fields) {
        return Stream.of(fields).filter(
                myField -> myField.isAnnotationPresent(Column.class) || myField.isAnnotationPresent(Join.class)
        ).collect(Collectors.toList());
    }

    private Method getSetterMethod(Class<?> modelClass, Field field) throws NoSuchMethodException {
        String fieldName = field.getName();
        Class<?> fieldType = field.getType();
        String setterMethodName = "set" + capitalize(fieldName);
        return modelClass.getMethod(setterMethodName, fieldType);
    }

    private Object convertObject(Object value, Field field) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        String methodName = field.getAnnotation(MyConverter.class).methodName();
        Method convertMethod = AttributeConverter.class.getMethod(methodName, Object.class);
        return convertMethod.invoke(AttributeConverter.class.newInstance(), value);
    }

    private String capitalize(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
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

    private String getSQLSelectAllCommand(Class<?> modelClass) throws ClassNotFoundException {
        if (isJoinAnnotationPresent(modelClass)) {
            return buildSQLSelectAllCommand(modelClass);
        } else {
            String tableName = getTableName(modelClass);
            String selectCommand = "SELECT * FROM " + tableName + ";";
            return selectCommand;
        }
    }

    private String getSQLSelectCommand(Class<?> modelClass, int id) throws ClassNotFoundException {
        if (isJoinAnnotationPresent(modelClass)) {
            return buildSQLSelectCommand(modelClass, id);
        } else {
            String tableName = getTableName(modelClass);
            String selectCommand = "SELECT * FROM tableName WHERE tableName.id = givenId;";
            selectCommand = selectCommand.replace("tableName", tableName);
            selectCommand = selectCommand.replace("givenId", String.valueOf(id));
            return selectCommand;
        }
    }

    private String buildSQLSelectCommand(Class<?> modelClass, int id) throws ClassNotFoundException {
        String selectAllCommand = buildSQLSelectAllCommand(modelClass);
        selectAllCommand = removeLastChar(selectAllCommand);
        String selectSpecificCommand = selectAllCommand + " WHERE tableName.id = givenId;";
        String tableName = getTableName(modelClass);
        selectSpecificCommand = selectSpecificCommand.replace("tableName", tableName);
        selectSpecificCommand = selectSpecificCommand.replace("givenId", String.valueOf(id));
        return selectSpecificCommand;
    }

    private String buildSQLSelectAllCommand(Class<?> modelClass) throws ClassNotFoundException {
        String tableName = getTableName(modelClass);
        List<Pair<String, String>> columnsTypesAndNames = getColumnsTypesAndNames(modelClass);
        Class<?> joinClass = Class.forName(getReferenceClass(modelClass));
        List<Pair<String, String>> joinColumnsTypesAndNames = getColumnsTypesAndNames(joinClass);
        Pair<String, String> referenceColumnAndHisMatching = getReferenceColumnAndHisMatching(modelClass);
        String joinTableName = getJoinTableName(modelClass);
        String selectCommand = "SELECT tables.columns FROM tableName " +
                "JOIN joinTableName " +
                "ON tableName.referenceColumn = joinTableName.matchingColumn;";
        String columns = columnsTypesAndNames.stream().map(pair -> tableName + "." + pair.getValue())
                .collect(Collectors.joining(" , "));
        String joinColumns = joinColumnsTypesAndNames.stream().map(pair -> joinTableName + "." + pair.getValue())
                .collect(Collectors.joining(" , "));

        selectCommand = selectCommand.replace("tables.columns", String.join(" , ", columns, joinColumns));
        selectCommand = selectCommand.replace("tableName", tableName);
        selectCommand = selectCommand.replace("joinTableName", joinTableName);
        selectCommand = selectCommand.replace("referenceColumn", referenceColumnAndHisMatching.getKey());
        selectCommand = selectCommand.replace("matchingColumn", referenceColumnAndHisMatching.getValue());
        return selectCommand;
    }

    private String removeLastChar(String string) {
        return string.substring(0, string.length() - 1);
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
        return getValueForSpecificField(getJoinEntity(entity), columnName);
    }

    private String getValueForSpecificField(Object entity, String columnName) {
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

    private Object getJoinEntity(Object entity) {
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
                                    + " INTEGER" + " NOT NULL";
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