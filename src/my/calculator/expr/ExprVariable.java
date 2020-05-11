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

    public ExprVariable(String nom) {
        NAME = nom;
    }

    @Override
    public double valeur(Environnement env) {
        return env.valeur(NAME);
    }

    @Override
    public String description() {
        return "la variable " + NAME;
    }

}