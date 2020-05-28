package my.calculator.expr;

/**
 * The binary expression used to compute an arithmetic expression
 * @author Matteo CARAVATI
 */
class ExprBinaire implements Expr {

    private final OpBinaire OP;
    private final Expr LEFT;
    private final Expr RIGHT;

    public ExprBinaire(Expr left, OpBinaire op, Expr right) {
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

    @Override
    public String getCode(int numRegistre) {
        String opcode = "";
        switch (OP.toString()) {
            case "PLUS":
                opcode = "add";
                break;
            case "MINUS":
                opcode = "sub";
                break;
            case "MULTIPLY":
                opcode = "mul";
                break;
            case "DIVIDE":
                opcode = "div";
                break;
        }
        
        LEFT.getCode(numRegistre);
        RIGHT.getCode(numRegistre + 1);
        return String.format("%s\n%s\n%s    r%d, r%d", LEFT.getCode(numRegistre), RIGHT.getCode(numRegistre + 1), opcode, numRegistre, numRegistre + 1);
    }

}
