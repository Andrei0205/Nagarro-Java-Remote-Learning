package com.nagarro.remotelearning.service;

import com.nagarro.remotelearning.model.Gender;

import java.time.LocalDate;

public class AttributeConverter {
    public Object convertStringToLocalDate(Object date) {
        String dateAsString = (String) date;
        return LocalDate.parse(dateAsString);
    }
    public Object convertStringToGender(Object gender) {
        String genderAsString = (String) gender;
        if("MALE".equals(genderAsString)){
            return Gender.MALE;
        }
        else{
            return Gender.FEMALE;
        }
    }
}
