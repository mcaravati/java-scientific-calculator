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

    final String NOM;
    Expr valeur;

    public ExprAffectation(String NOM, Expr valeur) {
        this.NOM = NOM;
        this.valeur = valeur;
    }
    
    @Override
    public double valeur(Environnement env) {
        double ret = valeur.valeur(env);
        env.affecter(NOM, ret);
        return ret;
    }

    @Override
    public String description() {
        return "la variable " + NOM;
    }
    
}
