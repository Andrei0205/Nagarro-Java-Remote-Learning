package com.nagarro.remotelearning.model;

import com.nagarro.remotelearning.annotations.Column;
import com.nagarro.remotelearning.annotations.Table;

@Table(name = "address")
public class Address {
    @Column(name = "id", type = "INTEGER", primaryKey = true, allowNull = false)
    private int id;

    @Column(name = "street", type = "VARCHAR(255)")
    private String street;

    @Column(name = "number", type = "VARCHAR(20)")
    private String number;

    @Column(name = "city", type = "VARCHAR(55)")
    private String city;

    @Column(name = "country", type = "VARCHAR(55)")
    private String country;

    public Address(int id,String street, String number, String city, String country) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.city = city;
        this.country = country;
    }
}
