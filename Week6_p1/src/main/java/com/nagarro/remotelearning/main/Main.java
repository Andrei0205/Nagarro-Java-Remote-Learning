package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.domain.Employee;
import com.nagarro.remotelearning.domain.Engine;
import com.nagarro.remotelearning.domain.EngineComponent;
import com.nagarro.remotelearning.factory.EngineFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
         EngineFactory engineFactory;
         List<EngineComponent> engineComponents = new ArrayList<>();

         Employee nonContainedEmployee;
         List<Employee> employees;
         EngineComponent e1 = new EngineComponent("arbore",200);
         EngineComponent e2 = new EngineComponent("injector",12);
         EngineComponent e3 = new EngineComponent("piston",20);

        employees = Arrays.asList(
                new Employee("Marian",false,true) ,
                new Employee("Andrei",false,false));
        engineComponents.add(e1);
        engineComponents.add(e2);
        engineComponents.add(e3);
        nonContainedEmployee = new Employee("Ionel",true,false);
        engineFactory = new EngineFactory(employees,engineComponents);
        List<Engine> engines = engineFactory.manufactureEngines(
                1,
                employees.get(0));
    }
}