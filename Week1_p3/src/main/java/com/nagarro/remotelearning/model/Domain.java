package com.nagarro.remotelearning.model;

public class Domain {
    private final String name;
    private final String ownerDetails;
    private final String hosts;

    public Domain(String name, String ownerDetails, String hosts) {
        this.name = name;
        this.ownerDetails = ownerDetails;
        this.hosts = hosts;
    }

    @Override
    public String toString() {
        return "Domain{" +
                "name='" + name + '\'' +
                ", ownerDetails='" + ownerDetails + '\'' +
                ", hosts='" + hosts + '\'' +
                '}';
    }
}
