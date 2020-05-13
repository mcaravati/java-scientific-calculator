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
class ExprVariable implements Expr {

    final String NAME;

    public ExprVariable(String name) {
        NAME = name;
    }

    @Override
    public double value(Environnement env) {
        return env.value(NAME);
    }

    @Override
    public String description() {
        return "The variable " + NAME;
    }

}