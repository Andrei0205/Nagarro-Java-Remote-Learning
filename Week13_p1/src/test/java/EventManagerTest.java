import com.nagarro.remotelearning.utils.Event;
import com.nagarro.remotelearning.utils.EventManager;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


public class EventManagerTest {
    @Test
    public void addValidEventTest() {
        EventManager eventManager = new EventManager();
        assertTrue(eventManager.addEvent(LocalDateTime.of(2023, 01, 02, 9, 30),
                LocalDateTime.of(2023, 01, 02, 12, 00), "local meet", null));
    }

    @Test
    public void addInvalidEventTest() {
        EventManager eventManager = new EventManager();
        assertFalse(eventManager.addEvent(LocalDateTime.of(2023, 01, 04, 9, 30),
                LocalDateTime.of(2023, 01, 02, 12, 00), "local meet", null));
    }

    @Test
    public void getEventsFromNextWeekendTest() {
        EventManager eventManager = new EventManager();
        eventManager.addEvent(LocalDateTime.of(2023, 04, 29, 9, 30),
                LocalDateTime.of(2023, 04, 29, 12, 00), "local meet", Optional.of("Ispirescu"));

        eventManager.addEvent(LocalDateTime.of(2023, 04, 30, 9, 30),
                LocalDateTime.of(2023, 04, 30, 12, 00), "theatre", Optional.of("A.I.Cuza"));

        eventManager.addEvent(LocalDateTime.of(2023, 04, 26, 9, 30),
                LocalDateTime.of(2023, 04, 26, 12, 00), "presentation", null);

        assertEquals(2, eventManager.getEventsFromNextWeekend().size());
    }

    @Test
    public void getEventsOnSpecificDateAndZoneTest() {
        EventManager eventManager = new EventManager();
        eventManager.addEvent(LocalDateTime.of(2023, 04, 29, 9, 30),
                LocalDateTime.of(2023, 04, 29, 12, 00), "local meet", Optional.of("Ispirescu"));
        List<Event> expectedList = new ArrayList<>();
        expectedList.add(new Event(LocalDateTime.of(2023, 04, 29, 8, 30),
                LocalDateTime.of(2023, 04, 29, 11, 00), "local meet", Optional.of("Ispirescu")));

        assertTrue(expectedList.equals(eventManager.getEventsOnSpecificDateAndZone(LocalDate.of(2023, 04, 29), ZoneId.of("Europe/Brussels"))));
    }

    @Test
    public void getEventsOnSpecificInterval() {
        EventManager eventManager = new EventManager();
        eventManager.addEvent(LocalDateTime.of(2022, 06, 12, 9, 30),
                LocalDateTime.of(2022, 06, 12, 12, 00), "local meet", Optional.of("Ispirescu"));

        eventManager.addEvent(LocalDateTime.of(2022, 06, 13, 9, 30),
                LocalDateTime.of(2022, 06, 13, 12, 00), "theatre", Optional.of("A.I.Cuza"));

        eventManager.addEvent(LocalDateTime.of(2022, 04, 26, 9, 30),
                LocalDateTime.of(2022, 04, 26, 12, 00), "presentation", null);

        assertEquals(2, eventManager.getEventsOnSpecificInterval(LocalDateTime.of(2022, 06, 10, 10, 00),
                LocalDateTime.of(2022, 06, 20, 23, 40)).size());
    }
}
