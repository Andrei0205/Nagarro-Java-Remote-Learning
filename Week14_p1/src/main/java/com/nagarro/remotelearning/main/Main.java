package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.model.Address;
import com.nagarro.remotelearning.model.Student;
import com.nagarro.remotelearning.service.DatabasePersistence;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        DatabasePersistence dbPers = new DatabasePersistence();
        //dbPers.createTable(Student.class);
       // Address address = new Address(6,"Ispirescu","23A","Bucuresti","Romania");
        dbPers.selectAll(Student.class);
       // dbPers.addEntity(new Student(4,"Mihaela","6010205163219", LocalDate.of(2006,10,21),address));



    }
}