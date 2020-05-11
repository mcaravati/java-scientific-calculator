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
public interface Expr {
    double valeur(Environnement env);
    
    public static Expr constante(double valeur) {
        return new ExprConstante(valeur);
    }
    public static Expr binaire(Expr gauche, OpBinaire op, Expr droite) {
        return new ExprBinaire(gauche, op, droite);
    }
    public static Expr variable(String nom) {
        return new ExprVariable(nom);
    }
    public static Expr affectation(String nom, Expr valeur) {
        return new ExprAffectation(nom, valeur);
    }

    public String description();
}
