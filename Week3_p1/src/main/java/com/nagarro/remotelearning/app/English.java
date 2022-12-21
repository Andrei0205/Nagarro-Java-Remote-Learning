package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class English extends Person {
    public English(String surname, String firstname, String birthDate) {
        super(surname, firstname);
        this.birthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public English(String fullname, String birthDate) {
        super(fullname);
        this.birthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public void selfDescribe() {
        String age = calculateAge();
        System.out.println("Hello,I'm from England and I like to read. I am " + age + " years old");
    }

}
