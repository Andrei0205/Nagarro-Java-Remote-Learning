
import com.nagarro.remotelearning.domain.Employee;
import com.nagarro.remotelearning.domain.Engine;
import com.nagarro.remotelearning.domain.EngineComponent;
import com.nagarro.remotelearning.exception.InsufficientStockException;
import com.nagarro.remotelearning.exception.UnauthorizedEmployeeException;
import com.nagarro.remotelearning.exception.UnqualifiedEmployeeException;
import com.nagarro.remotelearning.factory.EngineFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class FactoryTest {
    private static final int QUALIFIED_EMPLOYEE_INDEX = 0;
    private static final int NON_QUALIFIED_EMPLOYEE_INDEX = 1;
    private static final int INSUFFICIENT_NUMBER_OF_ENGINES = 2;
    private static final int SUFFICIENT_NUMBER_OF_ENGINES = 1;
    private static final int NO_EXPECTED_ENGINES = 1;
    private static final int COMPONENTS_PER_ENGINE = 3;
    private EngineFactory engineFactory;
    private final List<EngineComponent> engineComponents = new ArrayList<>();

    private Employee nonContainedEmployee;
    private final List<Employee> employees = new ArrayList<>();
    private final Class<UnauthorizedEmployeeException> unauthorizedEmployeeException = UnauthorizedEmployeeException.class;
    private final Class<UnqualifiedEmployeeException> unqualifiedEmployeeException = UnqualifiedEmployeeException.class;
    private final Class<InsufficientStockException> insufficientStockException = InsufficientStockException.class;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        employees.add(new Employee("Marian",false,true));
        employees.add(new Employee("Andrei",false,false));
        engineComponents.add(new EngineComponent("arbore",200));
        engineComponents.add(new EngineComponent("injector",12));
        engineComponents.add(new EngineComponent("piston",20));
        nonContainedEmployee = new Employee("Ionel",true,false);
        engineFactory = new EngineFactory(employees,engineComponents);
    }

    @Test
    public void testNotContainedEmployee() {
        expectedException.expect(unauthorizedEmployeeException);
        expectedException.expectMessage(
                "Employee [" + nonContainedEmployee.getName() + "]is not authorized to work at this factory!");
        engineFactory.manufactureEngines(SUFFICIENT_NUMBER_OF_ENGINES,nonContainedEmployee);
    }
    @Test
    public void testNotQualifiedEmployee() {
        expectedException.expect(unqualifiedEmployeeException);
        expectedException.expectMessage(
                "Employee [" + employees.get(NON_QUALIFIED_EMPLOYEE_INDEX).getName() +
                        "] is not qualified to build engines!");
        engineFactory.manufactureEngines(SUFFICIENT_NUMBER_OF_ENGINES,employees.get(NON_QUALIFIED_EMPLOYEE_INDEX));
    }
    @Test
    public void testInssuficientStock() {
        expectedException.expect(insufficientStockException);
        expectedException.expectMessage(
                "Not enough stock to build [" + INSUFFICIENT_NUMBER_OF_ENGINES + "] engines!");
        engineFactory.manufactureEngines(INSUFFICIENT_NUMBER_OF_ENGINES,employees.get(QUALIFIED_EMPLOYEE_INDEX));
    }
    @Test
    public void testBuildEngine() {
        List<Engine> engines = engineFactory.manufactureEngines(
                SUFFICIENT_NUMBER_OF_ENGINES,
                employees.get(QUALIFIED_EMPLOYEE_INDEX));
        assertEquals(NO_EXPECTED_ENGINES,engines.size());
    }
    @Test
    public void testEmptyEngineComponentList() {
        List<EngineComponent> emptyEngineComponents = null;
        EngineFactory engineFactoryWithoutComponents = new EngineFactory(employees,emptyEngineComponents);
        expectedException.expect(insufficientStockException);
        expectedException.expectMessage(
                "Not enough stock to build [" + INSUFFICIENT_NUMBER_OF_ENGINES + "] engines!");
        engineFactoryWithoutComponents.manufactureEngines(
                INSUFFICIENT_NUMBER_OF_ENGINES,employees.get(QUALIFIED_EMPLOYEE_INDEX));
    }
    @Test
    public void testComponentsPerEngine() {
        assertEquals(COMPONENTS_PER_ENGINE,EngineFactory.getComponentsPerEngine());
    }
}
