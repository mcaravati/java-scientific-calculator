/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.calculator.expr;

import java.util.function.DoubleBinaryOperator;

/**
 *
 * @author owl
 */
public enum OpBinaire {

    PLUS((a, b) -> a + b),
    MOINS((a, b) -> a - b),
    FOIS((a, b) -> a * b),
    DIVISE((a, b) -> a/b);
    
    final DoubleBinaryOperator op;
    
    OpBinaire(DoubleBinaryOperator op) {
        this.op = op;
    }
    
    double appliquer(double a, double b) {
        return op.applyAsDouble(b, a);
    }
    
}