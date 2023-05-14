package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.model.Address;
import com.nagarro.remotelearning.model.Student;
import com.nagarro.remotelearning.service.DatabasePersistence;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        DatabasePersistence dbPers = new DatabasePersistence();
       // dbPers.createTable(Student.class);
        //Address address = new Address(20,"Dacia","34","Craiova","Romania");
        //dbPers.addEntity(address);
//       System.out.println(dbPers.getSQLSelectCommand(Address.class));
        dbPers.selectAll(Student.class);
       // dbPers.addEntity(new Student(13,"marian","5010205160012", LocalDate.of(2000,07,07),address));
       // dbPers.selectAll(Student.class);
        //dbPers.getColumnsTypes(Student.class);
        //System.out.println(dbPers.getSQLCreateCommandString("com.nagarro.remotelearning.model.Address"));
        //dbPers.addEntity(new Address(1,"str","16a","craiova","ro"));

    }
}