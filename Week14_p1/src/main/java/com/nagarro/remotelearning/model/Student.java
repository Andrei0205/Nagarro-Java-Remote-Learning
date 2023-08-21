package com.nagarro.remotelearning.model;

import com.nagarro.remotelearning.annotations.Column;
import com.nagarro.remotelearning.annotations.Join;
import com.nagarro.remotelearning.annotations.MyConverter;
import com.nagarro.remotelearning.annotations.Table;
import com.nagarro.remotelearning.service.DataManipulator;

import java.time.LocalDate;


@Table(name = "student")
public class Student {
    private final DataManipulator dataManipulator = new DataManipulator();
    @Column(name = "id", type = "INT", primaryKey = true, allowNull = false)
    private int id;

    @Column(name = "name", type = "VARCHAR(55)")
    private String name;

    @Column(name = "cnp", type = "VARCHAR(13)", unique = true)
    private String cnp;
    @MyConverter(methodName = "convertStringToGender")
    @Column(name = "gender", type = "VARCHAR(10)")
    private Gender gender;
    @MyConverter(methodName = "convertStringToLocalDate")
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
        this.gender = dataManipulator.deduceGenderFromCNP(cnp);
    }

    public Student() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cnp='" + cnp + '\'' +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", address=" + address +
                '}';
    }

}
