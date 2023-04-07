
import com.nagarro.remotelearning.domain.Employee;
import com.nagarro.remotelearning.exception.UnqualifiedEmployeeException;
import com.nagarro.remotelearning.factory.EngineFactory;
import com.nagarro.remotelearning.service.EmployeeService;

import static org.easymock.EasyMock.*;
import org.easymock.*;
import org.junit.*;



public class FactoryTest {

    Employee employee;
    @Rule
    public EasyMockRule rule = new EasyMockRule(this);

    @Mock
    private EmployeeService mock;

    @TestSubject
    private final EngineFactory classUnderTest = new EngineFactory(mock);

    @Before
    public void setUp() {
        employee = new Employee("Dorel");
    }

    @Test
    public void manufactureEngineTest() {
        expect(mock.isAssemblyLineWorker(anyObject())).andReturn(true);
        replay(mock);
        classUnderTest.manufactureEngines(1,employee);
    }
    @Test(expected = UnqualifiedEmployeeException.class)
    public void manufactureEngineTest2() {
        expect(mock.isAssemblyLineWorker(anyObject())).andReturn(false);
        replay(mock);
        classUnderTest.manufactureEngines(1,employee);
    }



}
