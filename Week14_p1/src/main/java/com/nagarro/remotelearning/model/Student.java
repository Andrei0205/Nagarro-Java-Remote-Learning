package com.nagarro.remotelearning.model;

import com.nagarro.remotelearning.annotations.Column;
import com.nagarro.remotelearning.annotations.Constraints;
import com.nagarro.remotelearning.annotations.Table;

import java.time.LocalDate;

@Table(name = "Student")
public class Student {
    @Column(name = "Name", type = "VARCHAR(55)")
    private String name;

    @Column(name = "Name", type = "VARCHAR(13)", constraints = @Constraints(primaryKey = true, unique = true))
    private String cnp;

    @Column(name = "Gender", type = "ENUM")
    private Gender gender;

    @Column(name = "BirthDate", type = "DATE")
    private LocalDate dateOfBirth;

    @Column(name = "Adress", type = "INTEGER")
    private int adress;
}
