package com.nagarro.remotelearning.model;

import com.nagarro.remotelearning.annotations.Column;
import com.nagarro.remotelearning.annotations.Constraints;
import com.nagarro.remotelearning.annotations.Table;

@Table(name = "Adress")
public class Adress {
    @Column(name = "Id", type = "INTEGER", constraints = @Constraints(primaryKey = true, unique = true))
    private int id;

    @Column(name = "Street", type = "VARCHAR(255)")
    private String street;

    @Column(name = "Number", type = "INTEGER")
    private int number;

    @Column(name = "City", type = "VARCHAR(55)")
    private String city;

    @Column(name = "Country", type = "VARCHAR(55)")
    private String country;
}
