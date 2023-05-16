package com.nagarro.remotelearning.model;

import com.nagarro.remotelearning.annotations.Column;
import com.nagarro.remotelearning.annotations.Converter;
import com.nagarro.remotelearning.annotations.Table;

@Converter(name = "convertToAddress")
@Table(name = "address")
public class Address {
    @Column(name = "id", type = "INTEGER", primaryKey = true, allowNull = false)
    private final int id;

    @Column(name = "street", type = "VARCHAR(255)")
    private final String street;

    @Column(name = "number", type = "VARCHAR(20)")
    private final String number;

    @Column(name = "city", type = "VARCHAR(55)")
    private final String city;

    @Column(name = "country", type = "VARCHAR(55)")
    private final String country;

    public Address(int id, String street, String number, String city, String country) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
