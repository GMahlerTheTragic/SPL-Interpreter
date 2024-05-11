package interpreter;

import interpreter.exceptions.VariableUndefinedException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class Environment {

    private final LinkedList<HashMap<String, Value>> variableMapContainer = new LinkedList<>();

    /**
     * initialize using one scope and one variable map
     */
    public void init() {
        variableMapContainer.addFirst(new HashMap<>());
    }

    /**
     * enter new scope by appending a new variable map
     */
    public void enterNewScope() {
        variableMapContainer.addFirst(new HashMap<>());
    }

    /**
     * remove last variable map
     */
    public void leaveCurrentScope() {
        variableMapContainer.remove();
    }

    /**
     * find variable from inner scope to outer scope and assign value or if non-existent, create variable
     */
    public void assignVariable(String identifier, Value value) {
        for (HashMap<String, Value> variableMap : variableMapContainer) {
            if (variableMap.containsKey(identifier)) {
                variableMap.put(identifier, value);
                return;
            }
        }
        HashMap<String, Value> currentScopeVariables = variableMapContainer.peek();
        Objects.requireNonNull(currentScopeVariables).put(identifier, value);
    }

    /**
     * find variable from inner scope to outer scope
     */
    public Value findVariable(String identifier) {
        for (HashMap<String, Value> variableMap : variableMapContainer) {
            if (variableMap.containsKey(identifier)) {
                return variableMap.get(identifier);
            }
        }
        throw new VariableUndefinedException("Variable '" + identifier + "' not found");
    }

    public List<HashMap<String, Value>> getVariableMapContainer() {
        return variableMapContainer;
    }
}
