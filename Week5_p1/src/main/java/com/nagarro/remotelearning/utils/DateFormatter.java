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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Day.getDayAtIndex(dayOfWeekValue)).append(" ");
        stringBuilder.append(Month.getMonthAtIndex(monthValue)).append(" ");
        stringBuilder.append(localDate.getYear());
        return stringBuilder.toString();

    }
}
