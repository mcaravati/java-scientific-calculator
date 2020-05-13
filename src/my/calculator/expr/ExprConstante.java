/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.calculator.expr;

/**
 *
 * @author owl
 */
class ExprConstante implements Expr {
    

    private final double VALUE;

    ExprConstante(double value) {
        this.VALUE = value;
    }

    @Override
    public double value(Environnement env) {
        return VALUE;
    }

    @Override
    public String description() {
        return String.format("The constant %d", VALUE);
    }
    
    
}
