package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Domain;

public class Registrar {
    private Registry innerRegistry;

    public Registrar() {
        innerRegistry = new Registry();
    }

    public Domain talkToTheRegistryToCreateDomain(String domainName, String ownerDetails, String hosts) {
        System.out.println("Registrar talk to the registry for create domain");
        return innerRegistry.createDomain(domainName, ownerDetails, hosts);
    }
}
