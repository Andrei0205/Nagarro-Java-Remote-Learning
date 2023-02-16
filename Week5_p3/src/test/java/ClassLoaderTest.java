import com.nagarro.remotelearning.utils.Factory;
import com.nagarro.remotelearning.utils.MyClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import static org.junit.Assert.assertEquals;

public class ClassLoaderTest {
    MyClass expectedMyClass;
    Object expectedReloadedClass;
    Factory factory;


    @Before
    public void setUp() {
        this.expectedMyClass = new MyClass();
        this.factory = new Factory();
        try {
            File file = new File("D:\\DynamicClass\\target\\classes");

            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};

            ClassLoader classLoader = new URLClassLoader(urls);

            expectedReloadedClass = classLoader.loadClass("org.example.utils.MyClass");
        } catch (MalformedURLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInitialClassLoader() throws MalformedURLException, ClassNotFoundException {
        Object actualInitialClass = factory.getClass("initial");
        assertEquals(actualInitialClass.getClass(), expectedMyClass.getClass());
    }
    @Test
    public void testReloadedClassLoader() throws MalformedURLException, ClassNotFoundException {
        Object actualReloadedClass = factory.getClass("reloaded");
        assertEquals(actualReloadedClass.getClass(), expectedReloadedClass.getClass());
    }
}
