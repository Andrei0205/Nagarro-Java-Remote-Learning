package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.PersonsFilter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<String> persons = Arrays.asList("18:Maria", "18:Marioara", "17:Ionut", "15:Marcel", "21:Vasile", "90:Mihai");
        PersonsFilter personsFilter = new PersonsFilter();
        personsFilter.getPersonsOlderThan(persons, 18).forEach(System.out::println);
        System.out.println(personsFilter.getOldestPerson(persons));
        System.out.println(personsFilter.checkIfPersonsAreYoungerThan(persons, 80));
        Map<Integer, List<String>> personsMap = personsFilter.groupByAge(persons);
        personsMap.forEach((key, value) -> System.out.println(key + ":" + value));
    }
}