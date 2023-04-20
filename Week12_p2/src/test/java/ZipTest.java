import org.junit.Test;

import java.util.stream.Stream;

import static com.nagarro.remotelearning.utils.StreamZip.zip;
import static org.junit.Assert.*;

public class ZipTest {


    @Test
    public void zeroElementsZipTest() {
        Stream<String> stream = zip(Stream.of(), Stream.of());
        assertEquals(0, stream.count());
    }

    @Test
    public void equalElementsZipTest() {
        Stream<String> stream = zip(Stream.of("1", "2", "3"), Stream.of("a", "b", "d"));
        assertEquals(6, stream.count());
    }

    @Test
    public void differentNumberOfElementsZipTest() {
        Stream<String> stream = zip(Stream.of("1", "2", "3"), Stream.of("a", "b"));
        assertEquals(4, stream.count());
    }

}
