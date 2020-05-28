package my.calculator.expr;

/**
 * The constant expression, used to declare constants such as 1 or 21
 * @author Matteo CARAVATI
 */
class ExprConstante implements Expr {
    

    private final double VALUE;

    ExprConstante(double value) {
        this.VALUE = value;
    }

    @Override
    public double value(Environnement env) {
        return VALUE;
    }

    @Override
    public String description() {
        return String.format("The constant %d", VALUE);
    }
    
    @Override
    public String getCode(int numeroRegistre) {
        return String.format("li    r%d, %f", numeroRegistre, VALUE);
    }
    
}
