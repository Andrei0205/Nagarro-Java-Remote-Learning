package com.nagarro.remotelearning.model;

public class Domain {
    private String name;
    private String ownerDetails;
    private String hosts;

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
