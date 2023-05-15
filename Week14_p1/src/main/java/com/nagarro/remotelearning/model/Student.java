package com.nagarro.remotelearning.model;

import com.nagarro.remotelearning.annotations.Column;
import com.nagarro.remotelearning.annotations.Converter;
import com.nagarro.remotelearning.annotations.Join;
import com.nagarro.remotelearning.annotations.Table;
import com.nagarro.remotelearning.service.DataConverter;

import java.time.LocalDate;

@Converter(name = "convertToStudent")
@Table(name = "student")
public class Student {
    DataConverter dataConverter = new DataConverter();
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
        this.gender = dataConverter.deduceGenderFromCNP(cnp);
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
