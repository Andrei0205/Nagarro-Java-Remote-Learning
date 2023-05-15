package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.model.Address;
import com.nagarro.remotelearning.model.Student;
import com.nagarro.remotelearning.service.DatabasePersistence;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        DatabasePersistence dbPers = new DatabasePersistence();
        //dbPers.createTable(Student.class);
        //Address address = new Address(4,"Enescu","44G","Ramnicu Valcea","Romania");
        //dbPers.addEntity(address);
//       System.out.println(dbPers.getSQLSelectCommand(Address.class));
        dbPers.selectAll(Student.class);
       // dbPers.addEntity(new Student(2,"Paul","5010205168899", LocalDate.of(2010,02,15),address));
       // dbPers.selectAll(Student.class);
        //dbPers.getColumnsTypes(Student.class);
        //System.out.println(dbPers.getSQLCreateCommandString("com.nagarro.remotelearning.model.Address"));
        //dbPers.addEntity(new Address(1,"str","16a","craiova","ro"));

    }
}