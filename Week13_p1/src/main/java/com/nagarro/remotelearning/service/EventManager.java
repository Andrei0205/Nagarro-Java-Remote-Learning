package com.nagarro.remotelearning.service;

import com.nagarro.remotelearning.model.Event;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class EventManager {
    private final List<Event> events = new ArrayList<>();

    public boolean addEvent(LocalDateTime startDate, LocalDateTime endDate, String summary, String location) {
        if (endDate.isAfter(startDate)) {
            return events.add(new Event(startDate, endDate, summary, location));
        }
        throw new UnsupportedOperationException("End Date must be after Start Date");
    }

    public List<Event> getEventsFromNextWeekend() {
        LocalDateTime nextSaturday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)).atStartOfDay();
        LocalDateTime nextSunday = nextSaturday.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).withHour(23).withMinute(59);

        return events.stream().filter(event ->
                event.getStartDate().isAfter(nextSaturday) &&
                        event.getEndDate().isBefore(nextSunday)).collect(Collectors.toList());

    }

    public List<Event> getEventsOnSpecificDateAndZone(LocalDate date, ZoneId zoneId) {
        return events.stream().filter(event ->
                        event.getStartDate().toLocalDate().isEqual(date) &&
                                event.getEndDate().toLocalDate().isEqual(date)).
                peek(event -> event.setStartDate(adaptDateToZoneId(event.getStartDate(), zoneId))).
                peek(event -> event.setEndDate(adaptDateToZoneId(event.getEndDate(), zoneId))).
                collect(Collectors.toList());
    }

    public List<Event> getEventsOnSpecificInterval(LocalDateTime startDate, LocalDateTime endDate) {
        return events.stream().filter(event ->
                event.getStartDate().isAfter(startDate) &&
                        event.getEndDate().isBefore(endDate)).collect(Collectors.toList());
    }

    private LocalDateTime adaptDateToZoneId(LocalDateTime localDateTime, ZoneId zoneId) {
        ZonedDateTime localDateWithDefaultTimeZone = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        ZonedDateTime adaptedZoneDate = localDateWithDefaultTimeZone.withZoneSameInstant(zoneId);
        return adaptedZoneDate.toLocalDateTime();
    }
}
