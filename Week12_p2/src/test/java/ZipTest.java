import org.junit.Test;

import java.util.stream.Stream;

import static com.nagarro.remotelearning.utils.StreamZip.zip;
import static org.junit.Assert.*;

public class ZipTest {
    @Test
    public void perform() {
        Stream<String> stream = zip(Stream.of("1", "2", "3"), Stream.of("a", "b"));
        assertEquals(4, stream.count());
        stream = zip(Stream.of("1", "2", "3"), Stream.of("a", "b", "d", "e"));
        assertEquals(6, stream.count());
    }

}
