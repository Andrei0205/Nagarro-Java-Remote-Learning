package com.nagarro.remotelearning.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ConsoleApp {
    private EventManager eventManager = new EventManager();
    private BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

    public void start() throws IOException {
        while (true) {
            System.out.println("App runing");
            System.out.println("Enter " +
                    "1 - add Event " +
                    "2 - List events taking place next weekend " +
                    "3 - Lists all events taking place on specific date with your time zone " +
                    "4 - List events taking place in a specific date time interval ");
            int option = Integer.parseInt(bufferRead.readLine());
            switch (option) {
                case 1:
                    consoleAddEvent();
                    break;
                case 2:
                    System.out.println(eventManager.getEventsFromNextWeekend());
                    break;
                case 3:
                    consoleListEventsOnSpecificDateAndTimeZone();
                    break;
                case 4:
                    consoleListEventsInASpecificInterval();
                    break;
                default:
                    System.out.println("error");
            }

        }
    }

    private void consoleListEventsInASpecificInterval() throws IOException {
        System.out.println("Enter an event with following pattern: ");
        System.out.println("Start date : yyyy-MM-dd HH:mm");
        String startDateString = bufferRead.readLine();
        System.out.println("End date : yyyy-MM-dd HH:mm");
        String endDateString = bufferRead.readLine();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
        LocalDateTime endDate = LocalDateTime.parse(endDateString, dateTimeFormatter);
        System.out.println(eventManager.getEventsOnSpecificInterval(startDate, endDate));
    }

    private void consoleListEventsOnSpecificDateAndTimeZone() throws IOException {
        System.out.println("Enter a date with following pattern: yyyy-MM-dd");
        String dateString = bufferRead.readLine();
        System.out.println("Enter zone id:  Ex: Europe/Brussels");
        String zoneId = bufferRead.readLine();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, dateFormatter);
        System.out.println(eventManager.getEventsOnSpecificDateAndZone(date, ZoneId.of(zoneId)));
    }

    private void consoleAddEvent() throws IOException {

        System.out.println("Enter an event with following pattern:");
        System.out.println("Start date : yyyy-MM-dd HH:mm");
        String startDateString = bufferRead.readLine();
        System.out.println("End date : yyyy-MM-dd HH:mm");
        String endDateString = bufferRead.readLine();
        System.out.println("Description ");
        String description = bufferRead.readLine();
        System.out.println("Location ");
        Optional<String> location = Optional.ofNullable(bufferRead.readLine());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
        LocalDateTime endDate = LocalDateTime.parse(endDateString, dateTimeFormatter);
        eventManager.addEvent(startDate, endDate, description, location);
    }

}

/*
 2023-04-29 10:20
 2023-04-29 23:40

 2023-04-29 08:00
 2023-04-30 09:00
 */