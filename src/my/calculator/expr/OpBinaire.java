package my.calculator.expr;

import java.util.function.DoubleBinaryOperator;

/**
 * The 4 main arithmetic operations
 * @author Matteo CARAVATI
 */
public enum OpBinaire {

    /**
     * Computes a + b
     */
    PLUS((a, b) -> a + b),

    /**
     * Computes a - b
     */
    MINUS((a, b) -> a - b),

    /**
     * Computes a * b
     */
    MULTIPLY((a, b) -> a * b),

    /**
     * Computes a / b
     */
    DIVIDE((a, b) -> a/b),
    
    /**
     * Computes a%b
     */
    MODULO((a, b) -> a%b);
    
    final DoubleBinaryOperator op;
    
    OpBinaire(DoubleBinaryOperator op) {
        this.op = op;
    }
    
    double appliquer(double a, double b) {
        return op.applyAsDouble(a, b);
    }
    
}