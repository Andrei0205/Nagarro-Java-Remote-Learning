package com.nagarro.remotelearning.main;
import com.nagarro.remotelearning.model.DispatchOperator;
import com.nagarro.remotelearning.model.TaxiDriver;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<TaxiDriver> drivers = new ArrayList<TaxiDriver>();
        drivers.add(new TaxiDriver("Vlad"));
        DispatchOperator operator = new DispatchOperator("Dan", drivers);

        operator.dispatch("Calea Motilor nr. 6");
    }
}