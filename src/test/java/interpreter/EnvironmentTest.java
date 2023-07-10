package interpreter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class EnvironmentTest {

    private Environment environment;

    @BeforeEach
    public void setup() {
        environment = new Environment();
        environment.init();
    }

    @Test
    public void testInit() {
        LinkedList<HashMap<String, Value>> variableMapContainer = environment.getVariableMapContainer();
        assertNotNull(variableMapContainer);
        assertEquals(1, variableMapContainer.size());
        assertTrue(variableMapContainer.getFirst().isEmpty());
    }

    @Test
    public void testEnterNewScope() {
        environment.enterNewScope();
        LinkedList<HashMap<String, Value>> variableMapContainer = environment.getVariableMapContainer();
        assertNotNull(variableMapContainer);
        assertEquals(2, variableMapContainer.size());
        assertTrue(variableMapContainer.getFirst().isEmpty());
    }

    @Test
    public void testLeaveCurrentScope() {
        environment.enterNewScope();
        environment.leaveCurrentScope();
        LinkedList<HashMap<String, Value>> variableMapContainer = environment.getVariableMapContainer();
        assertNotNull(variableMapContainer);
        assertEquals(1, variableMapContainer.size());
    }

    @Test
    public void testAssignVariableInCurrentScope() {
        environment.assignVariable("x", new Value(42.0));
        LinkedList<HashMap<String, Value>> variableMapContainer = environment.getVariableMapContainer();
        assertNotNull(variableMapContainer);
        assertEquals(1, variableMapContainer.size());
        HashMap<String, Value> currentScopeVariables = variableMapContainer.getFirst();
        assertEquals(1, currentScopeVariables.size());
        assertTrue(currentScopeVariables.containsKey("x"));
        assertEquals(new Value(42.0), currentScopeVariables.get("x"));
    }

    @Test
    public void testAssignVariableInOuterScope() {
        environment.enterNewScope();
        environment.assignVariable("x", new Value(42.0));
        environment.leaveCurrentScope();
        LinkedList<HashMap<String, Value>> variableMapContainer = environment.getVariableMapContainer();
        assertNotNull(variableMapContainer);
        assertEquals(1, variableMapContainer.size());
        HashMap<String, Value> currentScopeVariables = variableMapContainer.getFirst();
        assertEquals(0, currentScopeVariables.size());
        assertFalse(currentScopeVariables.containsKey("x"));
    }

    @Test
    public void testAssignVariableExistingInInnerScope() {
        environment.enterNewScope();
        environment.assignVariable("x", new Value(10.0));
        environment.enterNewScope();
        environment.assignVariable("x", new Value(20.0));
        LinkedList<HashMap<String, Value>> variableMapContainer = environment.getVariableMapContainer();
        assertNotNull(variableMapContainer);
        assertEquals(3, variableMapContainer.size());
        HashMap<String, Value> innerScopeVariables = variableMapContainer.getFirst();
        HashMap<String, Value> outerScopeVariables = variableMapContainer.get(1);
        assertEquals(0, innerScopeVariables.size());
        assertFalse(innerScopeVariables.containsKey("x"));
        assertEquals(1, outerScopeVariables.size());
        assertTrue(outerScopeVariables.containsKey("x"));
        assertEquals(new Value(20.0), outerScopeVariables.get("x"));
    }

    @Test
    public void testAssignVariableNonExistingInAnyScope() {
        environment.assignVariable("x", new Value(42.0));
        LinkedList<HashMap<String, Value>> variableMapContainer = environment.getVariableMapContainer();
        assertNotNull(variableMapContainer);
        assertEquals(1, variableMapContainer.size());
        HashMap<String, Value> currentScopeVariables = variableMapContainer.getFirst();
        assertEquals(1, currentScopeVariables.size());
        assertTrue(currentScopeVariables.containsKey("x"));
        assertEquals(new Value(42.0), currentScopeVariables.get("x"));
    }

    @Test
    public void testFindVariableExistingInCurrentScope() {
        environment.init();
        environment.assignVariable("x", new Value(42.0));
        Value result = environment.findVariable("x");
        assertEquals(new Value(42.0), result);
    }

    @Test
    public void testFindVariableExistingInOuterScope() {
        environment.assignVariable("x", new Value(42.0));
        environment.enterNewScope();
        Value result = environment.findVariable("x");
        assertEquals(new Value(42.0), result);
    }

    @Test
    public void testFindVariableNonExistingInAnyScope() {
        environment.init();
        assertThrows(RuntimeException.class, () -> environment.findVariable("x"));
    }
}

