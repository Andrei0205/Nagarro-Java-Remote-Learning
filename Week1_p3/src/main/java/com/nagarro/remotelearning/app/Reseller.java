package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Domain;

public class Reseller {
    private Registrar innerRegistrar;

    public Reseller() {
        innerRegistrar = new Registrar();
    }

    public Domain contactRegistrarToBuyDomain(String domainName, String ownerDetails, String hosts) {
        System.out.println("Reseller call registrar for deal to the registry");
        return innerRegistrar.talkToTheRegistryToCreateDomain(domainName, ownerDetails, hosts);
    }
}
