package com.nagarro.remotelearning.service;

import com.nagarro.remotelearning.model.Address;
import com.nagarro.remotelearning.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class DatabaseTypeConverter {

    public Student convertToStudent(ResultSet resultSet) throws SQLException {
        int id = convertToInteger(resultSet, 1);
        String name = convertToString(resultSet, 2);
        String cnp = convertToString(resultSet, 3);
        LocalDate birthDate = convertToDate(resultSet, 5);
        Address address = convertToAddressFromStudent(resultSet, 6);
        return new Student(id, name, cnp, birthDate, address);

    }

    public Address convertToAddress(ResultSet resultSet) throws SQLException {
        int id = convertToInteger(resultSet, 1);
        String street = convertToString(resultSet, 2);
        String number = convertToString(resultSet, 3);
        String city = convertToString(resultSet, 4);
        String country = convertToString(resultSet, 5);
        return new Address(id, street, number, city, country);
    }

    private Address convertToAddressFromStudent(ResultSet resultSet, int columnIndex) throws SQLException {
        int id = convertToInteger(resultSet, columnIndex);
        String street = convertToString(resultSet, columnIndex + 1);
        String number = convertToString(resultSet, columnIndex + 2);
        String city = convertToString(resultSet, columnIndex + 3);
        String country = convertToString(resultSet, columnIndex + 4);
        return new Address(id, street, number, city, country);
    }


    private Integer convertToInteger(ResultSet resultSet, int columnIndex) throws SQLException {
        int value = resultSet.getInt(columnIndex);
        if (resultSet.wasNull()) {
            return null;
        }
        return value;
    }

    private String convertToString(ResultSet resultSet, int columnIndex) throws SQLException {
        String value = resultSet.getString(columnIndex);
        if (resultSet.wasNull()) {
            return null;
        }
        return value;
    }

    private LocalDate convertToDate(ResultSet resultSet, int columnIndex) throws SQLException {
        java.sql.Date date = resultSet.getDate(columnIndex);
        if (resultSet.wasNull()) {
            return null;
        }
        return date.toLocalDate();
    }

}
