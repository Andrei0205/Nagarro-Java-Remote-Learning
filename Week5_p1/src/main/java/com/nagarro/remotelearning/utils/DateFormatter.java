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
        for (Day day : Day.values()) {
            if (dayOfWeekValue == day.getIndex()) {
                stringBuilder.append(day).append(" ");
                break;
            }
        }
        for (Month month : Month.values()) {
            if (monthValue == month.getIndex()) {
                stringBuilder.append(month).append(" ");
                break;
            }
        }
        stringBuilder.append(localDate.getYear());
        return stringBuilder.toString();

    }
}
