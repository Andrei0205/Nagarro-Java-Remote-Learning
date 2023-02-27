
import com.nagarro.remotelearning.domain.Employee;
import com.nagarro.remotelearning.domain.Engine;
import com.nagarro.remotelearning.exception.UnauthorizedEmployeeException;
import com.nagarro.remotelearning.exception.UnqualifiedEmployeeException;
import com.nagarro.remotelearning.factory.EngineFactory;
import com.nagarro.remotelearning.service.EmployeeService;

import static org.easymock.EasyMock.*;
import org.easymock.*;
import org.junit.*;

import java.util.List;
import static org.junit.Assert.assertEquals;

public class FactoryTest {

    Employee employee;
    @Rule
    public EasyMockRule rule = new EasyMockRule(this);

    @Mock
    private EmployeeService mock;

    @TestSubject
    private EngineFactory classUnderTest = new EngineFactory(mock);

    @Before
    public void setUp() {
        employee = new Employee("Dorel");
    }

    @Test
    public void manufactureEngineTest() {
        expect(mock.isAssemblyLineWorker(employee)).andReturn(true);
        replay(mock);
        classUnderTest.manufactureEngines(1,employee);
    }



}
