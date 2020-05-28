package my.calculator.expr;

/**
 * The affectation expression, to apply a variable to the evironment
 * @author Matteo CARAVATI
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

    @Override
    public String getCode(int numRegistre) {
        return(String.format("%s\nst    r%d, %s", value.getCode(numRegistre), numRegistre, NAME));
    }
    
    
}
