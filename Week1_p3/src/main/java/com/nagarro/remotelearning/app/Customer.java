package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Domain;

public class Customer {

    private Domain domain = null;

    private final Reseller reseller;

    public Customer(Reseller reseller) {
        this.reseller = reseller;

    }

    public void buyDomain(String domainName, String ownerDetails, String hosts) {
        System.out.println("Customer call reseller for buying domain");
        domain = reseller.contactRegistrarToBuyDomain(domainName, ownerDetails, hosts);
        System.out.println(domain);
    }
}
