package com.nagarro.remotelearning.model;

public interface Dispatcher {
    void dispatch(String location);
    TaxiDriver getBestAvailableTaxi(String location);
}
