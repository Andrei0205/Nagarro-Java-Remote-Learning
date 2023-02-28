import com.nagarro.remotelearning.utils.ClassType;
import com.nagarro.remotelearning.utils.MyClassFactory;
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
    MyClass expectedReloadedClass;
    MyClass expectedSubclass;
    MyClassFactory factory;


    @Before
    public void setUp() {
        this.expectedMyClass = new MyClass();
        this.factory = new MyClassFactory();
        try {
            File file = new File("D:\\DynamicClass\\target\\classes");

            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};

            ClassLoader classLoader = new URLClassLoader(urls);

            expectedReloadedClass = (MyClass) classLoader.loadClass("com.nagarro.remotelearning.utils.MyClass").newInstance();
        } catch (MalformedURLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            File file = new File("D:\\DynamicClass\\target\\classes");

            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};

            ClassLoader classLoader = new URLClassLoader(urls);

            expectedSubclass = (MyClass) classLoader.loadClass("com.nagarro.remotelearning.utils.SubClass").newInstance();
        } catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testInitialClassLoader() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        MyClass actualInitialClass = factory.getClass(ClassType.INITIAL);
        assertEquals(actualInitialClass.getClass(), expectedMyClass.getClass());
    }
    @Test
    public void testReloadedClassLoader() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        MyClass actualReloadedClass = factory.getClass(ClassType.RELOADED);
        assertEquals(actualReloadedClass.getClass(), expectedReloadedClass.getClass());
    }
    @Test
    public void testSubclassLoader() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        MyClass actualSubclass = factory.getClass(ClassType.SUBCLASS);
        assertEquals(actualSubclass.getClass(), expectedSubclass.getClass());
    }
}
