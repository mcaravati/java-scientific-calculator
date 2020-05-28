package my.calculator.expr;

/**
 * Environment interface 
 * @author Matteo CARAVATI
 */
public interface Environnement {

    /**
     * Applies the name and the value to the environment
     * @param name variable name
     * @param value variable value 
     */
    void apply(String name, double value);

    /**
     * Gets the value of the variable in the environment
     * @param name the variable name
     * @return the associated value
     */
    double  value(String name);
}
