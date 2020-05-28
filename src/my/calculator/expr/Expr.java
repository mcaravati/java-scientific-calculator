package my.calculator.expr;

/**
 * Expression interface
 * @author Matteo CARAVATI
 */
public interface Expr {

    /**
     * Computes the expression's value
     * @param env the associated environment
     * @return the expression's value
     */
    double value(Environnement env);
    
    /**
     * Decalres a constant Expression
     * @param value the value to assign to the expression
     * @return a new ExrContante
     */
    public static Expr constant(double value) {
        return new ExprConstante(value);
    }

    /**
     * Declares a binary expression
     * @param left the left expression
     * @param op the operator
     * @param right the right expression
     * @return a new ExprBinaire
     */
    public static Expr binary(Expr left, OpBinaire op, Expr right) {
        return new ExprBinaire(left, op, right);
    }

    /**
     * Declares a new variable expression
     * @param name the variable's name
     * @return a new ExprVariable
     */
    public static Expr variable(String name) {
        return new ExprVariable(name);
    }

    /**
     * Declares a new affectation expression
     * @param name the variable's name
     * @param value the variable's value 
     * @return a new ExprAffectation
     */
    public static Expr affectation(String name, Expr value) {
        return new ExprAffectation(name, value);
    }
    
    /**
     * Gets the assembly code for the expression with default register 1
     * @return the assembly instructions of the expression
     */
    default public String getCode() {
        return getCode(1);
    }
    
    /**
     * Gets the assembly code for the expression
     * @param numRegistre the default register
     * @return the assembly instructions of the expression
     */
    String getCode(int numRegistre);

    /**
     * Gets the expression's description
     * @return the expression's description
     */
    public String description();
}
