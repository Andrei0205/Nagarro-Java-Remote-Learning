package com.nagarro.remotelearning.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class Event {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private final String summary;
    private final String location; //default = "n/a" 2 constructors

    public Event(LocalDateTime startDate, LocalDateTime endDate, String summary, String location) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.summary = summary;
        this.location = location;
    }
    public Event(LocalDateTime startDate, LocalDateTime endDate, String summary) {
        this(startDate,endDate,summary,"n/a");
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Event{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", summary='" + summary + '\'' +
                ", location=" + location +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return startDate.equals(event.startDate) && endDate.equals(event.endDate) && summary.equals(event.summary) && Objects.equals(location, event.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, summary, location);
    }
}
