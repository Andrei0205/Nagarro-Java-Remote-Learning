package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Romanian extends Person {

    public Romanian(String surname, String firstname, String birthDate) {
        super(surname, firstname);
        this.birthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public Romanian(String fullname, String birthDate) {
        super(fullname);
        this.birthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    @Override
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public void selfDescribe() {
        calculateAge();
        System.out.println("Buna,sunt din romania si imi place sa calatoresc. Varsta mea este: " + age);
    }

}
