package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Dispatcher;
import com.nagarro.remotelearning.model.Person;

import java.util.List;

public class DispatchOperator implements Person, Dispatcher {

    private String name;
    private List<TaxiDriver> drivers;

    public DispatchOperator(String name, List<TaxiDriver> drivers) {
        this.name = name;
        this.drivers = drivers;
    }

    public void eat() {
        System.out.println("Operator " + name + " eats");
    }

    private TaxiDriver getBestAvailableTaxi(String location) {
        return drivers.get(0);
    }

    public void dispatch(String location) {
        getBestAvailableTaxi(location).goToAddress(location);
    }
}
