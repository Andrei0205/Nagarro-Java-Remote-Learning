package com.nagarro.remotelearning.main;


import com.nagarro.remotelearning.utils.DatabaseQuery;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        DatabaseQuery dbquery = new DatabaseQuery();
         System.out.println(dbquery.getAllClients());
        //System.out.println(dbquery.getBalanceForUser("cjirov"));
        //System.out.println(dbquery.getBusinessClients(BigDecimal.valueOf(100000)));
    }
}