package interpreter;

import java.util.LinkedList;
import java.util.HashMap;

public class Environment {

    private LinkedList<HashMap<String, Value>> variableMapContainer = new LinkedList<>();

    /**
	 initialize using one scope and one variable map
	 */
    public void init() {
        variableMapContainer.addFirst(new HashMap<String, Value>());
    }

    /**
	 enter new scope by appending a new variable map
	 */
    public void enterNewScope() {
        variableMapContainer.addFirst(new HashMap<String, Value>());
    }

    /**
	 remove last variable map
	 */
    public void leaveCurrentScope() {
        variableMapContainer.remove();
    }

    /**
	 find variable from inner scope to outer scope and assign value or if non exist, create variable
	 */
    public void assignVariable(String identifier, Value value) {
        for (HashMap<String, Value> variabelMap : variableMapContainer) {
            if (variabelMap.containsKey(identifier)) {
                variabelMap.put(identifier, value);
                return;
            }
        }
        HashMap<String, Value> currentScopeVariables = variableMapContainer.peek();
        currentScopeVariables.put(identifier, value);
    }

    /**
	 find variable from inner scope to outer scope
	 */
    public Value findVariable(String identifier) {
        for (HashMap<String, Value> variabelMap : variableMapContainer) {
            if (variabelMap.containsKey(identifier)) {
                return variabelMap.get(identifier);
            }
        }
        throw new RuntimeException();
    }

    public LinkedList<HashMap<String, Value>> getVariableMapContainer() {
        return variableMapContainer;
    }
}
