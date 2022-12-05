package com.nagarro.remotelearning.utils;

import com.nagarro.remotelearning.model.Person;

public class PersonCreator {
    public static final int FIRST_NAME_INDEX = 0;
    public static final int LAST_NAME_INDEX = 1;
    public static final int DOB_INDEX = 2;
    public static final int DOD_INDEX = 3;

    public static Person createPerson(String line) {
        String[] split = line.split(",");
        if (split.length < 4) {
            return new Person(split[FIRST_NAME_INDEX],split[LAST_NAME_INDEX],split[DOB_INDEX]);
        }else {
            return new Person(split[FIRST_NAME_INDEX],split[LAST_NAME_INDEX],split[DOB_INDEX],split[DOD_INDEX]);
        }
    }
}
