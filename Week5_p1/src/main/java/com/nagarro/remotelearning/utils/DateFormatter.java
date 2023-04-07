package com.nagarro.remotelearning.utils;

import com.nagarro.remotelearning.enums.Day;
import com.nagarro.remotelearning.enums.Month;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    public String formatDate(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        int dayOfWeekValue = localDate.getDayOfWeek().getValue();
        int monthValue = localDate.getMonth().getValue();
        String stringBuilder = Day.getDayAtIndex(dayOfWeekValue) + " " +
                Month.getMonthAtIndex(monthValue) + " " +
                localDate.getYear();
        return stringBuilder;

    }
}
