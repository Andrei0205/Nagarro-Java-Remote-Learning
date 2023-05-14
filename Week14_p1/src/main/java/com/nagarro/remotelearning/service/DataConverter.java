package com.nagarro.remotelearning.service;

import com.nagarro.remotelearning.model.Gender;

public class DataConverter {
    public Gender deduceGenderFromCNP(String cnp) {
        char year = cnp.charAt(1);
        char gender = cnp.charAt(0);
        if (year == '0' || year == '1' || year == '2') {
            if (gender == '5') {
                return Gender.MALE;
            }
            return Gender.FEMALE;
        } else {
            if (gender == '1') {
                return Gender.MALE;
            }
            return Gender.FEMALE;
        }
    }
}
