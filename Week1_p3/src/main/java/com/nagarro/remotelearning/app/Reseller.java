package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Domain;

public class Reseller {
    private final Registrar registrar;

    public Reseller(Registrar registrar) {
        this.registrar = registrar;
    }

    public Domain contactRegistrarToBuyDomain(String domainName, String ownerDetails, String hosts) {
        System.out.println("Reseller call registrar for deal to the registry");
        return registrar.talkToTheRegistryToCreateDomain(domainName, ownerDetails, hosts);
    }
}
