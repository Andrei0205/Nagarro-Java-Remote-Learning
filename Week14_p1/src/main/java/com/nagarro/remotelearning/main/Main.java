package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.model.Address;
import com.nagarro.remotelearning.service.DBPers;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        DBPers dbPers = new DBPers();
        System.out.println(dbPers.createSQLString("com.nagarro.remotelearning.model.Address"));
        dbPers.addEntity(new Address(1,"str","16a","craiova","ro"));
        //dbPers.createSQLString("com.nagarro.remotelearning.model.Address");
    }
}