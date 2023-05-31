package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.model.Address;
import com.nagarro.remotelearning.model.Student;
import com.nagarro.remotelearning.service.DatabasePersistence;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        DatabasePersistence dbPers = new DatabasePersistence();
        //dbPers.createTable(Address.class);
        //Address address = new Address(78,"Ispirescu","23A","Bucuresti","Romania");
       // dbPers.selectAll(Student.class);
        dbPers.selectSpecificEntity(Address.class,78);
        dbPers.update(Student.class,123,"name","Andreea");
        //dbPers.addEntity(new Student(122,"Mihaela","6010205163219", LocalDate.of(2006,10,21),address));



    }
}