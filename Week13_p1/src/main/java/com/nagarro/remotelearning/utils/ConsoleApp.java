package com.nagarro.remotelearning.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleApp {
    private EventManager eventManager = new EventManager();
    private Scanner console = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("App runing");
            System.out.println("Enter " +
                    "1 - add Event " +
                    "2 - List events taking place next weekend " +
                    "3 - Lists all events taking place on specific date with your time zone " +
                    "4 - List events taking place in a specific date time interval ");
            int option = console.nextInt();
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

    private void consoleListEventsInASpecificInterval() {
        System.out.println("Enter an event with following pattern: ");
        System.out.println("Start date : yyyy-MM-dd HH:mm");
        String startDateString = console.nextLine();
        System.out.println("End date : yyyy-MM-dd HH:mm");
        String endDateString = console.nextLine();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
        LocalDateTime endDate = LocalDateTime.parse(endDateString, dateTimeFormatter);
        System.out.println(eventManager.getEventsOnSpecificInterval(startDate, endDate));
    }

    private void consoleListEventsOnSpecificDateAndTimeZone() {
        System.out.println("Enter a date with following pattern: yyyy-MM-dd");
        String dateString = console.nextLine();
        System.out.println("Enter zone id:  Ex: Europe/Brussels");
        String zoneId = console.nextLine();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, dateFormatter);
        System.out.println(eventManager.getEventsOnSpecificDateAndZone(date, ZoneId.of(zoneId)));
    }

    private void consoleAddEvent() {

        System.out.println("Enter an event with following pattern:");
        System.out.println("Start date : yyyy-MM-dd HH:mm");
        String startDateString = console.nextLine();
        System.out.println("End date : yyyy-MM-dd HH:mm");
        String endDateString = console.nextLine();
        System.out.println("Description ");
        String description = console.nextLine();
        System.out.println("Location ");
        Optional<String> location = Optional.ofNullable(console.nextLine());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
        LocalDateTime endDate = LocalDateTime.parse(endDateString, dateTimeFormatter);
        eventManager.addEvent(startDate, endDate, description, location);
    }

}
