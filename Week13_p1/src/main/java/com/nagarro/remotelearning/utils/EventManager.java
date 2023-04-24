package com.nagarro.remotelearning.utils;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class EventManager {
    private final List<Event> events = new ArrayList<>();

    public boolean addEvent(LocalDateTime startDate, LocalDateTime endDate, String summary, Optional<String> location) {
        if (endDate.isAfter(startDate)) {
            return events.add(new Event(startDate, endDate, summary, location));
        }
        return false;
    }

    public List<Event> getEventsFromNextWeekend() {
        LocalDateTime nextSaturday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)).atStartOfDay();
        LocalDateTime nextSunday = nextSaturday.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).withHour(23).withMinute(59);

        return events.stream().filter(event ->
                event.getStartDate().isAfter(nextSaturday) &&
                        event.getEndDate().isBefore(nextSunday)).collect(Collectors.toList());

    }

    public List<Event> getEventsOnSpecificDateAndZone(LocalDate date, ZoneId zoneId) {
        List<Event> eventsOnRequiredDate = events.stream().filter(event ->
                        event.getStartDate().toLocalDate().isEqual(date) &&
                                event.getEndDate().toLocalDate().isEqual(date))
                .collect(Collectors.toList());
        for (Event event : eventsOnRequiredDate) {
            event.setStartDate(adaptDate(event.getStartDate(), zoneId));
            event.setEndDate(adaptDate(event.getEndDate(), zoneId));
        }
        return eventsOnRequiredDate;
        //.map(event -> event.setStartDate((event.getStartDate().atZone(zoneId)).toLocalDateTime()))
    }

    public List<Event> getEventsOnSpecificInterval(LocalDateTime startDate, LocalDateTime endDate) {
        return events.stream().filter(event ->
                event.getStartDate().isAfter(startDate) &&
                        event.getEndDate().isBefore(endDate)).collect(Collectors.toList());
    }

    private LocalDateTime adaptDate(LocalDateTime localDateTime, ZoneId zoneId) {
        ZonedDateTime defaultDate = ZonedDateTime.of(localDateTime, ZoneId.of("Europe/Bucharest"));
        ZonedDateTime adaptedZoneDate = defaultDate.withZoneSameInstant(zoneId);
        return adaptedZoneDate.toLocalDateTime();
    }
}
