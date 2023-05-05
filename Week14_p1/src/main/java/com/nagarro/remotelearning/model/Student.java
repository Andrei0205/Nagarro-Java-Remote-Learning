package com.nagarro.remotelearning.model;

import com.nagarro.remotelearning.annotations.Column;
import com.nagarro.remotelearning.annotations.Join;
import com.nagarro.remotelearning.annotations.Table;

import java.time.LocalDate;

@Table(name = "student")
public class Student {
    //database persistance - creare citire - generica
    //ad id
    //cand introduc un student ori adresa e salvata ori
    //save + read student
    @Column(name = "id", type = "INT", primaryKey = true, allowNull = false)
    private int id;

    @Column(name = "name", type = "VARCHAR(55)") // no uppercase
    private String name;

    @Column(name = "cnp", type = "VARCHAR(13)", unique = true)
    private String cnp;

    @Column(name = "gender", type = "VARCHAR(10)") // from cnp
    private Gender gender;

    @Column(name = "birth_date", type = "DATE")
    private LocalDate dateOfBirth;

    //join sql
    //add one annotation
    @Join(tableToJoin = "adress", joinByColumn = "id")
    @Column(name = "address_id", type = "INT")
    private Address address;
}
