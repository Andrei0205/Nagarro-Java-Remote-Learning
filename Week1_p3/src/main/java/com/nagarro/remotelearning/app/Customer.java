package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Domain;

public class Customer {
    private String domainName;
    private String ownerDetails;
    private String hosts;

    private Domain domain = null;

    private Reseller innerReseller;

    public Customer(String domainName, String ownerDetails, String hosts) {
        this.domainName = domainName;
        this.ownerDetails = ownerDetails;
        this.hosts = hosts;
        innerReseller = new Reseller();

    }

    public void buyDomain() {
        System.out.println("Customer call reseller for buying domain");
        domain = innerReseller.contactRegistrarToBuyDomain(domainName, ownerDetails, hosts);
        System.out.println(domain);
    }
}
