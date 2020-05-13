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
class ExprAffectation implements Expr {

    final String NAME;
    Expr value;

    public ExprAffectation(String NAME, Expr value) {
        this.NAME = NAME;
        this.value = value;
    }
    
    @Override
    public double value(Environnement env) {
        double ret = value.value(env);
        env.apply(NAME, ret);
        return ret;
    }

    @Override
    public String description() {
        return "The variable " + NAME;
    }
    
}
