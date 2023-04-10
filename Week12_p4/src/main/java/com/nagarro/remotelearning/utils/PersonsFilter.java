package com.nagarro.remotelearning.utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;


public class PersonsFilter {

    private static final String REGEX = ":";
    private static final int AGE_INDEX = 0;
    private static final int NAME_INDEX = 1;

    public Stream<Person> getPersonsOlderThan(List<String> persons, int age) {
        List<Person> splitPersons = splitListIntoPersons(persons);
        return splitPersons.stream().filter(p -> p.getAge() > age);
    }

    public Person getOldestPerson(List<String> persons) {
        List<Person> splitPersons = splitListIntoPersons(persons);
        Optional<Person> oldestPerson = splitPersons.stream().reduce((a, b) -> a.getAge() > b.getAge() ? a : b);
        return oldestPerson.orElseThrow(NoSuchElementException::new);
    }

    public String checkIfPersonsAreYoungerThan(List<String> persons, int age) {
        List<Person> splitPersons = splitListIntoPersons(persons);
        if (splitPersons.stream().allMatch(a -> a.getAge() < age)) {
            return "Yes";
        } else {
            return "No";
        }
    }

    public Map<Integer, List<String>> groupByAge(List<String> persons) {
        Stream<Person> personStream = splitListIntoPersons(persons).stream();
        return personStream.collect(Collectors
                .groupingBy(Person::getAge, Collectors.mapping(Person::getName, Collectors.toList())));
    }

    private List<Person> splitListIntoPersons(List<String> initialList) {
        List<Person> splitList = new ArrayList<>();
        for (String pers : initialList) {
            String[] dates = pers.split(REGEX);
            splitList.add(new Person(Integer.parseInt(dates[AGE_INDEX]), dates[NAME_INDEX]));
        }
        return splitList;
    }

}
