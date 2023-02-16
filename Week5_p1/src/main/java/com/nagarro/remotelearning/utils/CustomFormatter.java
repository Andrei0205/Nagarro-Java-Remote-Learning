package com.nagarro.remotelearning.utils;

import com.nagarro.remotelearning.enums.Day;
import com.nagarro.remotelearning.enums.Month;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomFormatter {
    public void format(String date) {
        LocalDate localDate;
        localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        int dayOfWeek = localDate.getDayOfWeek().getValue();
        int month = localDate.getMonth().getValue();
        System.out.println(Day.values()[dayOfWeek]);
        System.out.println(Month.values()[month]);
        System.out.println(localDate.getYear());
    }
}
