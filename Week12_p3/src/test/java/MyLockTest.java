import com.nagarro.remotelearning.utils.MyLock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.Assert.*;

public class MyLockTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void lockTest() {
        MyLock myLock = new MyLock();
        myLock.withLock(new ReentrantLock(), () -> System.out.println("Hello from lock"));
        assertEquals("Hello from lock", outputStreamCaptor.toString().trim());
    }
}
