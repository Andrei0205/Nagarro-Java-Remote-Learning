
import com.nagarro.remotelearning.utils.Person;
import com.nagarro.remotelearning.utils.PersonsFilter;
import org.junit.Test;

import java.util.*;

import java.util.stream.Stream;

import static org.junit.Assert.*;

public class PersonsFilterTest {
    private PersonsFilter personsFilter = new PersonsFilter();
    private List<Person> persons = personsFilter.splitListIntoPersons(Arrays.asList("18:Maria", "18:Marioara", "17:Ionut", "15:Marcel", "21:Vasile", "90:Mihai"));


    @Test
    public void getPersonsOlderThan18Test() {
        Stream<Person> expectedStream = personsFilter.splitListIntoPersons(Arrays.asList("21:Vasile", "90:Mihai")).stream();
        Stream<Person> filteredPersons = personsFilter.getPersonsOlderThan(persons, 18);
        assertStreamEquals(expectedStream, filteredPersons);
    }

    @Test
    public void getOldestPersonTest() {
        Person expectedPerson = new Person(90, "Mihai");
        Optional<Person> oldestPerson = personsFilter.getOldestPerson(persons);
        oldestPerson.ifPresent(person -> assertEquals(expectedPerson, person));
    }

    @Test
    public void checkIfAllPersonsAreYoungerThan18Test() {
        String expectedAnswer = "No";
        String answer = personsFilter.checkIfPersonsAreYoungerThan(persons, 18);
        assertEquals(expectedAnswer, answer);
    }

    @Test
    public void groupByAgeTest() {
        List<String> expectedList = new ArrayList<>(Arrays.asList("Maria", "Marioara"));
        Map<Integer, List<String>> groupedPersons = personsFilter.groupByAge(persons);
        assertEquals(groupedPersons.get(18), expectedList);
    }


    private void assertStreamEquals(Stream<?> firstStream, Stream<?> secondStream) {
        Iterator<?> firstIterator = firstStream.iterator(), secondIterator = secondStream.iterator();
        while (firstIterator.hasNext() && secondIterator.hasNext()) {
            assertEquals(firstIterator.next(), secondIterator.next());
        }
        assert !firstIterator.hasNext() && !secondIterator.hasNext();
    }
}
