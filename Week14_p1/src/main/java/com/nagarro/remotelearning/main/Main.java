package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.model.Address;
import com.nagarro.remotelearning.model.Student;
import com.nagarro.remotelearning.service.DatabasePersistence;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        DatabasePersistence dbPers = new DatabasePersistence();
       // dbPers.createTable(Student.class);
       // Address address = new Address(10,"Severinului","34","Bucuresti","Romania");
        //dbPers.addEntity(address);
       dbPers.getSQLSelectCommand(Student.class);
        //dbPers.selectAll(Student.class);
        //dbPers.addEntity(new Student(2,"andrei","5010205160023", LocalDate.of(2001,02,05),address));
       // dbPers.selectAll(Student.class);
        //dbPers.getColumnsTypes(Student.class);
        //System.out.println(dbPers.getSQLCreateCommandString("com.nagarro.remotelearning.model.Address"));
        //dbPers.addEntity(new Address(1,"str","16a","craiova","ro"));

    }
}