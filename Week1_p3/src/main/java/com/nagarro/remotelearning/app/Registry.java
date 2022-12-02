package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Domain;

public class Registry {
    public Domain createDomain(String domainName, String ownerDetails, String hosts) {
        System.out.println("Domain will be created");
        return new Domain(domainName, ownerDetails, hosts);
    }
}
