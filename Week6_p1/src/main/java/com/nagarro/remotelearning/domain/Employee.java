package com.nagarro.remotelearning.domain;


public class Employee {

    private final String name;
    private final boolean isAssemblyLineWorker;
    private final boolean isAdministrator;

    public Employee(String name, boolean isAdministrator, boolean isAssemblyLineWorker) {
        this.name = name;
        this.isAdministrator = isAdministrator;
        this.isAssemblyLineWorker = isAssemblyLineWorker;
    }

    public boolean isAssemblyLineWorker() {
        return isAssemblyLineWorker;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public String getName() {
        return name;
    }
}
