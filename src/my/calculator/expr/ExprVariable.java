package my.calculator.expr;

/**
 * The variable expression used to return a variable's value
 * @author Matteo CARAVATI
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
    
    @Override
    public String getCode(int numeroRegistre) {
        return String.format("li    r%d,%s", numeroRegistre, NAME);
    }

}