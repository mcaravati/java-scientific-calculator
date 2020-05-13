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
class ExprBinaire implements Expr {

    private final  OpBinaire OP;
    private final Expr LEFT;
    private final Expr RIGHT;

    public ExprBinaire( Expr left, OpBinaire op, Expr right) {
        this.OP = op;
        this.LEFT = left;
        this.RIGHT = right;
    }

    @Override
    public double value(Environnement env) {
         return OP.appliquer(LEFT.value(env), RIGHT.value(env));
    }
    
    @Override
    public String description() {
        return String.format("%s %s %s", 
                LEFT.description(), OP, RIGHT.description());
    }
    
}
