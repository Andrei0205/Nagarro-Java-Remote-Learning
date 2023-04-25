package com.nagarro.remotelearning.inteface;

import com.nagarro.remotelearning.service.EventManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.zone.ZoneRulesException;

public class ConsoleApp {
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private final EventManager eventManager = new EventManager();
    private final BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

    public void start() throws IOException {
        while (true) {
            System.out.println("App runing");
            System.out.println("Enter " +
                    "1 - add Event " +
                    "2 - List events taking place next weekend " +
                    "3 - Lists all events taking place on specific date with your time zone " +
                    "4 - List events taking place in a specific date time interval ");
            String option = bufferRead.readLine();
            switch (option) {
                case "1":
                    consoleAddEvent();
                    break;
                case "2":
                    System.out.println(eventManager.getEventsFromNextWeekend());
                    break;
                case "3":
                    consoleListEventsOnSpecificDateAndTimeZone();
                    break;
                case "4":
                    consoleListEventsInASpecificInterval();
                    break;
                default:
                    System.out.println("Please enter a Number between 1 and 4");
            }

        }
    }

    private void consoleListEventsInASpecificInterval() {
        System.out.println("Enter an event with following pattern: ");
        LocalDateTime startDate = readDateTimeWithPattern(DATE_TIME_PATTERN, "Start Date");
        LocalDateTime endDate = readDateTimeWithPattern(DATE_TIME_PATTERN, "End Date");
        System.out.println(eventManager.getEventsOnSpecificInterval(startDate, endDate));
    }

    private void consoleListEventsOnSpecificDateAndTimeZone() {
        System.out.println("Enter a date with following pattern: yyyy-MM-dd");
        LocalDate date = readDateWithPattern(DATE_PATTERN);
        ZoneId zoneId = readZoneId();
        System.out.println(eventManager.getEventsOnSpecificDateAndZone(date, zoneId));
    }

    private void consoleAddEvent() throws IOException {
        System.out.println("Enter an event with following pattern:");
        LocalDateTime startDate = readDateTimeWithPattern(DATE_TIME_PATTERN, "Start Date");
        LocalDateTime endDate = readDateTimeWithPattern(DATE_TIME_PATTERN, "End Date");
        System.out.println("Description ");
        String description = bufferRead.readLine();
        System.out.println("Location ");
        String location = bufferRead.readLine();
        eventManager.addEvent(startDate, endDate, description, location);
    }

    private ZoneId readZoneId() {
        while (true) {
            try {
                System.out.println("Enter zone id:  Ex: Europe/Brussels");
                String zoneId = bufferRead.readLine();
                if (!ZoneId.getAvailableZoneIds().contains(zoneId)) {
                    throw new ZoneRulesException("Unavailable zone");
                }
                return ZoneId.of(zoneId);
            } catch (IOException | ZoneRulesException e) {
                e.printStackTrace();
            }
        }
    }

    private LocalDateTime readDateTimeWithPattern(String dateTimeFormatterPattern, String whichDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormatterPattern);
        while (true) {
            System.out.println(whichDate + " date : " + dateTimeFormatterPattern);
            LocalDateTime date = null;
            try {
                String dateString = bufferRead.readLine();
                date = LocalDateTime.parse(dateString, dateTimeFormatter);
                return date;
            } catch (IOException | DateTimeParseException e) {
                e.printStackTrace();
                System.out.println("Enter a date with correct format");
            }
        }
    }

    private LocalDate readDateWithPattern(String dateFormatterPattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormatterPattern);
        while (true) {
            System.out.println("Date : " + dateFormatterPattern);
            LocalDate date = null;
            try {
                String dateString = bufferRead.readLine();
                date = LocalDate.parse(dateString, dateTimeFormatter);
                return date;
            } catch (IOException | DateTimeParseException e) {
                e.printStackTrace();
                System.out.println("Enter a date with correct format");
            }
        }
    }

}

/*
 2023-04-29 10:20
 2023-04-29 23:40

 2023-04-29 08:00
 2023-04-30 09:00
 */