//package com.nagarro.remotelearning.service;
//
//import com.nagarro.remotelearning.annotations.Table;
//
//import java.lang.annotation.*;
//import java.lang.reflect.*;
//import java.util.*;
//
//public class DatabasePersistance {
//
//    public void create(String modelClasses) throws ClassNotFoundException {
//
//        for (String className : modelClasses) {
//
//            Class<?> modelClass = Class.forName(className);
//            String tableName = getTableName(className, modelClass);
//            if (tableName == null) continue;
//            List<String> columnDefs = new ArrayList<>();
//            for (Field field : modelClass.getDeclaredFields()) {
//                String columnName;
//                Annotation[] anns = field.getDeclaredAnnotations();
//                if (anns.length < 1) {
//                    continue; // Not a db table column
//                }
//                if (anns[0] instanceof SQLInteger) {
//                    SQLInteger sInt = (SQLInteger) anns[0];
//// Use field name if name not specified
//                    if (sInt.name().length() < 1)
//                        columnName = field.getName().toUpperCase();
//                    else
//                        columnName = sInt.name();
//                    columnDefs.add(columnName + " INT" +
//                            getConstraints(sInt.constraints()));
//                }
//                if (anns[0] instanceof SQLString) {
//                    SQLString sString = (SQLString) anns[0];
//// Use field name if name not specified.
//                    if (sString.name().length() < 1)
//                        columnName = field.getName().toUpperCase();
//                    else
//                        columnName = sString.name();
//                    columnDefs.add(columnName + " VARCHAR(" +
//                            sString.value() + ")" +
//                            getConstraints(sString.constraints()));
//                }
//                StringBuilder createCommand = new StringBuilder(
//                        "CREATE TABLE " + tableName + "(");
//                for (String columnDef : columnDefs)
//                    createCommand.append("\n " + columnDef + ",");
//// Remove trailing comma
//                String tableCreate = createCommand.substring(
//                        0, createCommand.length() - 1) + ");";
//                System.out.println("Table Creation SQL for " +
//                        className + " is :\n" + tableCreate);
//            }
//        }
//    }
//
//    private String getTableName(String className, Class<?> modelClass) {
//        Table dbTable = modelClass.getAnnotation(Table.class);
//        if (dbTable == null) {
//            System.out.println(
//                    "No DBTable annotations in class " + className);
//            return null;
//        }
//        String tableName = dbTable.name();
//
//        if (tableName.length() < 1)
//            tableName = modelClass.getName().toUpperCase();
//        return tableName;
//    }
//
//    private String getConstraints(Constraints con) {
//        String constraints = "";
//        if (!con.allowNull())
//            constraints += " NOT NULL";
//        if (con.primaryKey())
//            constraints += " PRIMARY KEY";
//        if (con.unique())
//            constraints += " UNIQUE";
//        return constraints;
//    }
//}