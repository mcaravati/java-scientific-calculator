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
    

    private final double valeur;

    ExprConstante(double valeur) {
        this.valeur = valeur;
    }

    @Override
    public double valeur(Environnement env) {
        return valeur;
    }

    @Override
    public String description() {
        return String.format("la constante %d", valeur);
    }
    
    
}
