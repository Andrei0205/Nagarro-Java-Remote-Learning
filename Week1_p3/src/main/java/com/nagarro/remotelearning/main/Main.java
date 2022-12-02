package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.app.Customer;

public class Main {
    static Customer customer1 = new Customer("Preferential Domain", "Rich", "1,2,3");

    public static void main(String[] args) {
        customer1.buyDomain();
    }
}