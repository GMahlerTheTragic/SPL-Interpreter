package interpreter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class EnvironmentTest {

    private Environment environment;

    @BeforeEach
    public void setup() {
        environment = new Environment();
        environment.init();
    }

    @Test
    void testInit() {
        List<HashMap<String, Value>> variableMapContainer = environment.getVariableMapContainer();
        assertNotNull(variableMapContainer);
        assertEquals(1, variableMapContainer.size());
        assertTrue(variableMapContainer.get(0).isEmpty());
    }

    @Test
    void testEnterNewScope() {
        environment.enterNewScope();
        List<HashMap<String, Value>> variableMapContainer = environment.getVariableMapContainer();
        assertNotNull(variableMapContainer);
        assertEquals(2, variableMapContainer.size());
        assertTrue(variableMapContainer.get(0).isEmpty());
    }

    @Test
    void testLeaveCurrentScope() {
        environment.enterNewScope();
        environment.leaveCurrentScope();
        List<HashMap<String, Value>> variableMapContainer = environment.getVariableMapContainer();
        assertNotNull(variableMapContainer);
        assertEquals(1, variableMapContainer.size());
    }

    @Test
    void testAssignVariableInCurrentScope() {
        environment.assignVariable("x", new Value(42.0));
        List<HashMap<String, Value>> variableMapContainer = environment.getVariableMapContainer();
        assertNotNull(variableMapContainer);
        assertEquals(1, variableMapContainer.size());
        HashMap<String, Value> currentScopeVariables = variableMapContainer.get(0);
        assertEquals(1, currentScopeVariables.size());
        assertTrue(currentScopeVariables.containsKey("x"));
        assertEquals(new Value(42.0), currentScopeVariables.get("x"));
    }

    @Test
    void testAssignVariableInOuterScope() {
        environment.enterNewScope();
        environment.assignVariable("x", new Value(42.0));
        environment.leaveCurrentScope();
        List<HashMap<String, Value>> variableMapContainer = environment.getVariableMapContainer();
        assertNotNull(variableMapContainer);
        assertEquals(1, variableMapContainer.size());
        HashMap<String, Value> currentScopeVariables = variableMapContainer.get(0);
        assertEquals(0, currentScopeVariables.size());
        assertFalse(currentScopeVariables.containsKey("x"));
    }

    @Test
    void testAssignVariableExistingInInnerScope() {
        environment.enterNewScope();
        environment.assignVariable("x", new Value(10.0));
        environment.enterNewScope();
        environment.assignVariable("x", new Value(20.0));
        List<HashMap<String, Value>> variableMapContainer = environment.getVariableMapContainer();
        assertNotNull(variableMapContainer);
        assertEquals(3, variableMapContainer.size());
        HashMap<String, Value> innerScopeVariables = variableMapContainer.get(0);
        HashMap<String, Value> outerScopeVariables = variableMapContainer.get(1);
        assertEquals(0, innerScopeVariables.size());
        assertFalse(innerScopeVariables.containsKey("x"));
        assertEquals(1, outerScopeVariables.size());
        assertTrue(outerScopeVariables.containsKey("x"));
        assertEquals(new Value(20.0), outerScopeVariables.get("x"));
    }

    @Test
    void testAssignVariableNonExistingInAnyScope() {
        environment.assignVariable("x", new Value(42.0));
        List<HashMap<String, Value>> variableMapContainer = environment.getVariableMapContainer();
        assertNotNull(variableMapContainer);
        assertEquals(1, variableMapContainer.size());
        HashMap<String, Value> currentScopeVariables = variableMapContainer.get(0);
        assertEquals(1, currentScopeVariables.size());
        assertTrue(currentScopeVariables.containsKey("x"));
        assertEquals(new Value(42.0), currentScopeVariables.get("x"));
    }

    @Test
    void testFindVariableExistingInCurrentScope() {
        environment.init();
        environment.assignVariable("x", new Value(42.0));
        Value result = environment.findVariable("x");
        assertEquals(new Value(42.0), result);
    }

    @Test
    void testFindVariableExistingInOuterScope() {
        environment.assignVariable("x", new Value(42.0));
        environment.enterNewScope();
        Value result = environment.findVariable("x");
        assertEquals(new Value(42.0), result);
    }

    @Test
    void testFindVariableNonExistingInAnyScope() {
        environment.init();
        assertThrows(RuntimeException.class, () -> environment.findVariable("x"));
    }
}

