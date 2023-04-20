package com.nagarro.remotelearning.utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;


public class PersonsFilter {

    private static final String REGEX = ":";
    private static final int AGE_INDEX = 0;
    private static final int NAME_INDEX = 1;

    public Stream<Person> getPersonsOlderThan(List<Person> persons, int age) {
        return persons.stream().filter(person -> person.getAge() > age);
    }

    public Optional<Person> getOldestPerson(List<Person> persons) {
        return persons.stream().reduce((firstPerson, secondPerson) ->
                firstPerson.getAge() > secondPerson.getAge() ? firstPerson : secondPerson);
    }

    public String checkIfPersonsAreYoungerThan(List<Person> persons, int age) {
        if (persons.stream().allMatch(person -> person.getAge() < age)) {
            return "Yes";
        } else {
            return "No";
        }
    }

    public Map<Integer, List<String>> groupByAge(List<Person> persons) {
        Stream<Person> personStream = persons.stream();
        return personStream.collect(Collectors
                .groupingBy(Person::getAge, Collectors.mapping(Person::getName, Collectors.toList())));
    }

    public List<Person> splitListIntoPersons(List<String> initialList) {
        List<Person> splitList = new ArrayList<>();
        for (String pers : initialList) {
            String[] dates = pers.split(REGEX);
            splitList.add(new Person(Integer.parseInt(dates[AGE_INDEX]), dates[NAME_INDEX]));
        }
        return splitList;
    }

}
