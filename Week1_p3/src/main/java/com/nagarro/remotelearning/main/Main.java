package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.app.Customer;
import com.nagarro.remotelearning.app.Registrar;
import com.nagarro.remotelearning.app.Registry;
import com.nagarro.remotelearning.app.Reseller;

public class Main {


    public static void main(String[] args) {
        Registry registry = new Registry();
        Registrar registrar = new Registrar(registry);
        Reseller reseller = new Reseller(registrar);
        Customer customer = new Customer(reseller);
        customer.buyDomain("myDomain","rich","host.ro");
    }
}