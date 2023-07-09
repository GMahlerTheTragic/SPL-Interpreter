package parser;

import java.util.LinkedList;
import java.util.HashMap;

public class Environment {

    private LinkedList<HashMap<String, Value>> variableMapContainer = new LinkedList<>();

    public void init() {
        variableMapContainer.addFirst(new HashMap<String, Value>());
    }

    public void enterNewScope() {
        variableMapContainer.addFirst(new HashMap<String, Value>());
    }

    public void leaveCurrentScope() {
        variableMapContainer.remove();
    }

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

    public Value findVariable(String identifier) {
        for (HashMap<String, Value> variabelMap : variableMapContainer) {
            if (variabelMap.containsKey(identifier)) {
                return variabelMap.get(identifier);
            }  
        }
        throw new RuntimeException();
    }
}
