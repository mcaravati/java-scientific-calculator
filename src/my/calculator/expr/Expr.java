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
    double value(Environnement env);
    
    public static Expr constant(double value) {
        return new ExprConstante(value);
    }
    public static Expr binary(Expr left, OpBinaire op, Expr right) {
        return new ExprBinaire(left, op, right);
    }
    public static Expr variable(String name) {
        return new ExprVariable(name);
    }
    public static Expr affectation(String name, Expr value) {
        return new ExprAffectation(name, value);
    }

    public String description();
}
