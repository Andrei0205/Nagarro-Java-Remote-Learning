package com.nagarro.remotelearning.model;

import java.util.List;

public class DispatchOperator implements Person, Dispatcher {

    private final String name;
    private final List<TaxiDriver> drivers;

    public DispatchOperator(String name, List<TaxiDriver> drivers) {
        this.name = name;
        this.drivers = drivers;
    }

    public void eat() {
        System.out.println("Operator " + name + " eats");
    }

    @Override
    public void sleep() {

    }

    public TaxiDriver getBestAvailableTaxi(String location) {
        return drivers.get(0);
    }

    public void dispatch(String location) {
        getBestAvailableTaxi(location).goToAddress(location);
    }
}
