package com.nagarro.remotelearning.model;

import com.nagarro.remotelearning.annotations.Column;
import com.nagarro.remotelearning.annotations.Join;
import com.nagarro.remotelearning.annotations.Table;

import java.time.LocalDate;

@Table(name = "student")
public class Student {

    @Column(name = "id", type = "INT", primaryKey = true, allowNull = false)
    private int id;

    @Column(name = "name", type = "VARCHAR(55)")
    private String name;

    @Column(name = "cnp", type = "VARCHAR(13)", unique = true)
    private String cnp;

    @Column(name = "gender", type = "VARCHAR(10)")
    private Gender gender;

    @Column(name = "birth_date", type = "VARCHAR(30)")
    private LocalDate dateOfBirth;

    @Join(tableToJoin = "address", joinByColumn = "id")
    private Address address;

    public Student(int id, String name, String cnp, LocalDate dateOfBirth, Address address) {
        this.id = id;
        this.name = name;
        this.cnp = cnp;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.gender = calculateGender(cnp);
    }

    private Gender calculateGender(String cnp) { //todo  separately
        char year = cnp.charAt(1);
        char gender = cnp.charAt(0);
        if (year == '0' || year == '1' || year == '2') {
            if (gender == '5') {
                return Gender.MALE;
            }
            return Gender.FEMALE;
        } else {
            if (gender == '1') {
                return Gender.MALE;
            }
            return Gender.FEMALE;
        }
    }
}
