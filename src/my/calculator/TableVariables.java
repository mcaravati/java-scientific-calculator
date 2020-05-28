package my.calculator;

import java.util.HashMap;
import java.util.Map;

/**
 * Class managing the variables storage
 * @author Matteo CARAVATI
 */
class VariablesTable implements my.calculator.expr.Environnement {

    Map<String, Double> variables = new HashMap<>();

    /**
     * Applies a value to a variable
     * @param name The variable name
     * @param value Th evalue to be applied
     */
    public void apply(String name, double value) {
        variables.put(name, value);
    }

    /**
     * Gets the value of an existing variable
     * @param name The name of the variable
     * @return The variable's value
     */
    public double value(String name) {
        return variables.get(name);
    }

    /**
     * Checks if a variable exists
     * @param name The variable's name
     * @return True if the variable exists
     */
    public boolean contains(String name) {
        return variables.containsKey(name);
    }
}
