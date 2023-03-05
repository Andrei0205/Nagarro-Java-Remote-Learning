import com.nagarro.remotelearning.utils.Country;
import com.nagarro.remotelearning.utils.CountryComparator;
import com.nagarro.remotelearning.utils.CountryGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CountryTest {

    private static final int NO_OF_COUNTRIES_TO_GENERATE = 10;
    private static final int PARIS_INDEX = 1;
    private CountryGenerator countryGenerator;
    List<Country> countries;

    @Before
    public void setUp() {
        countryGenerator = new CountryGenerator();
    }

    @After
    public void after() {
        countries.clear();
    }

    @Test
    public void sortByNameTest() {
        countries = countryGenerator.generateRandom(NO_OF_COUNTRIES_TO_GENERATE);
        Collections.sort(countries);
        for (int index = 0; index < countries.size() - 1; index++) {
            assertTrue(countries.get(index).compareTo(countries.get(index + 1)) <= 0);
        }
    }

    @Test
    public void sortByCapitalTest() {
        countries = countryGenerator.generateRandom(NO_OF_COUNTRIES_TO_GENERATE);
        countries.sort(new CountryComparator());
        for (int index = 0; index < countries.size() - 1; index++) {
            assertTrue(countries.get(index).getCapital().compareTo(countries.get(index + 1).getCapital()) <= 0);
        }
    }

    @Test
    public void binarySearchTest() {
        countries = new ArrayList<>();
        countries.add(new Country("Spain", "Madrid"));
        countries.add(new Country("France", "Paris"));
        countries.add(new Country("Romania", "Bucharest"));
        assertEquals(Collections.binarySearch(countries, new Country("France", "Paris"), new CountryComparator()),
                PARIS_INDEX);
    }

}
