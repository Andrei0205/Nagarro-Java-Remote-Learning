package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.model.Address;
import com.nagarro.remotelearning.service.DatabasePersistence;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        DatabasePersistence dbPers = new DatabasePersistence();
        dbPers.createTable("com.nagarro.remotelearning.model.Address");
        Address address = new Address(1,"Bld Oltenia","16A","Craiova","Romania");
        dbPers.addEntity(address);
        dbPers.selectAll(Address.class);

        //System.out.println(dbPers.getSQLCreateCommandString("com.nagarro.remotelearning.model.Address"));
        //dbPers.addEntity(new Address(1,"str","16a","craiova","ro"));
        //dbPers.createSQLString("com.nagarro.remotelearning.model.Address");
    }
}