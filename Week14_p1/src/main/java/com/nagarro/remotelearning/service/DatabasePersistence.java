package com.nagarro.remotelearning.service;

import com.nagarro.remotelearning.annotations.Column;
import com.nagarro.remotelearning.annotations.Join;
import com.nagarro.remotelearning.annotations.Table;
import javafx.util.Pair;

import java.lang.annotation.Annotation;
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
            //executeTransactions(insertEntityCommand, insertReferenceEntityCommand);
            System.out.println(insertReferenceEntityCommand);
            System.out.println(insertEntityCommand);
        } else {
            String insertEntityCommand = getSQLInsertCommand(entity);
            // executeStatement(insertEntityCommand);
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
            List<String> referenceColumnName = getReferenceColumnsNames(modelClass);
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
        tableName = getTableName(modelClass);
        columnDefinitions = getColumnsDefinitions(modelClass);
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
        List<String> columns = getColumnsNames(entityClass);
        List<String> values = getColumnsValues(entity);
        if (isJoinAnnotationPresent(entity)) {
            columns.addAll(getReferenceColumnsNames(entityClass));
            values.addAll(getReferenceValue(entity));
        }

        return buildSQLInsertCommand(tableName, columns, values);
    }

    private List<String> getReferenceValue(Object entity) {
        Class<?> entityClass = entity.getClass();
        List<String> columnNames = Stream.of(entityClass.getDeclaredFields()).filter(
                field -> field.isAnnotationPresent(Join.class)
        ).map(field -> {
            Join joinAnnotation = field.getAnnotation(Join.class);
            return joinAnnotation.joinByColumn();
        }).collect(Collectors.toList());
        return getValueForSpecificColumn(getReferenceEntity(entity), columnNames);
    }

    private List<String> getValueForSpecificColumn(Object entity, List<String> columnNames) {
        Class<?> entityClass = entity.getClass();
        return Stream.of(entityClass.getDeclaredFields()).filter(
                field -> {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    return columnAnnotation.name().equals(columnNames.get(0));
                }
        ).map(field -> {
                    field.setAccessible(true);
                    try {
                        Object value =  field.get(entity);
                        return value.toString();
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

        ).collect(Collectors.toList());
    }

    private String buildSQLInsertCommand(String tableName, List<String> columns, List<String> values) { //pair or map columns-values
        StringBuilder insertCommand = new StringBuilder();
        //regex  /  string.join
        insertCommand.append("INSERT INTO ").append(tableName).append(" (");
        insertCommand.append(String.join(",", columns));
        insertCommand.append(" )").append("\n ");
        insertCommand.append("VALUES ").append("(");
        for (String value : values) {
            insertCommand.append("'").append(value).append("'").append(",");
        }
        insertCommand.deleteCharAt(insertCommand.length() - 1);
        insertCommand.append(" )").append(";");
        return insertCommand.toString();
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
            if (columnAnnotation != null && !field.isAnnotationPresent(Join.class)) {
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


    /**
     * Return a List of Strings with columns that match with reference fields declared in current class,
     * columns that you need for join condition
     *
     * @return List
     */
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

    /**
     * Return a list of Strings with all fields that have @Join annotation
     *
     * @return List
     */
    private List<String> getReferenceColumnsNames(Class<?> modelClass) {
        List<String> referenceColumnNames = new ArrayList<>();
        for (Field field : modelClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Join.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                referenceColumnNames.add(columnAnnotation.name());
            }
        }
        return referenceColumnNames;
    }

    /**
     * If you have @Join annotation in any field of your class, this method will get
     * the names for columns declared in referenced table
     *
     * @return List
     */
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
//        Field[] fields = entityClass.getDeclaredFields();
//       return Arrays.stream(fields).filter(
//               field -> field.isAnnotationPresent(Column.class) && field.isAnnotationPresent(Join.class)
//       ).map(field -> {
//           field.setAccessible(true);
//           try {
//               return field.get(entity);
//           } catch (IllegalAccessException e) {
//               throw new RuntimeException(e);
//           }
//       });
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Join.class)) {
                field.setAccessible(true);
                try {
                    Object value = field.get(entity);
                    return value;
                } catch (IllegalAccessException e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }
        throw new RuntimeException("not found");
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