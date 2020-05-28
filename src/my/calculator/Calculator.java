package my.calculator;

import java.lang.Math;
import java.util.HashMap;
import java.util.Map;
import my.calculator.expr.Expr;
import my.calculator.expr.OpBinaire;

/**
 * Class containing the core functions required by the Calculator to work
 *
 * @author Matteo CARAVATI
 */
public class Calculator {

    Tokenizer tokenizer;
    Token token;
    VariablesTable table = new VariablesTable();

    /**
     * Empty constructor because no argument is needed
     */
    public Calculator() {

    }

    /**
     * Calculates the value of the inserted expression
     *
     * @param line The user's input
     * @return The computed value
     * @throws SyntaxErrorException catch input errors
     * @throws EvaluationErrorException catch calculation errors such as division by 0
     */
    public double evaluation(String line) throws SyntaxErrorException, EvaluationErrorException {
        tokenizer = new Tokenizer(line.replaceAll(" +", ""));
        token = tokenizer.get();

        Expr total = get_tree_expr();
        System.out.println("Assembly code : \n" + total.getCode());

        checkSyntax(token.isEnd(), String.format("End of expression expected, %s found", token));
        return total.value(table);
    }

    private void checkSyntax(boolean state, String line) throws SyntaxErrorException {
        if (!state) {
            throw new SyntaxErrorException(line);
        }
    }

    private Expr get_tree_expr() throws SyntaxErrorException, EvaluationErrorException {
        Expr total = get_tree_term();
        Expr tmp;
        while (token.isSymbol() && !token.isEnd()) {
            switch (token.string) {
                case "+":
                    token = tokenizer.get();
                    tmp = get_tree_term();
                    total = Expr.binary(total, OpBinaire.PLUS, tmp);
                    break;
                case "-":
                    token = tokenizer.get();
                    tmp = get_tree_term();
                    total = Expr.binary(total, OpBinaire.MINUS, tmp);
                    break;
            }

        }
        return total;
    }

    private Expr get_tree_term() throws SyntaxErrorException, EvaluationErrorException {
        Expr total = get_tree_factor();
        Expr tmp;
        while (token.isPrioritySymbol() && !token.isEnd()) {
            switch (token.string) {
                case "/":
                    token = tokenizer.get();
                    if (token.isNumber() && token.value() == 0) {
                        throw new EvaluationErrorException("Division by 0");
                    }
                    tmp = get_tree_factor();
                    total = Expr.binary(total, OpBinaire.DIVIDE, tmp);
                    break;
                case "*":
                    token = tokenizer.get();
                    tmp = get_tree_factor();
                    total = Expr.binary(total, OpBinaire.MULTIPLY, tmp);
                    break;
                case "^":
                    token = tokenizer.get();
                    total = Expr.constant(Math.pow(total.value(table), get_tree_factor().value(table)));
                    break;
                case "%":
                    token = tokenizer.get();
                    Expr tok = get_tree_factor();
                    total = Expr.binary(
                            total,
                            OpBinaire.MODULO,
                            tok
                            );
                    break;
            }

        }
        return total;
    }

    private Expr get_number_value() throws SyntaxErrorException {
        checkSyntax(token.isNumber(), "Number expected");
        int value = token.value();
        token = tokenizer.get();
        return Expr.constant(value);
    }

    private Expr get_tree_factor() throws SyntaxErrorException, EvaluationErrorException {
        Expr total = null;
        if (token.isNumber()) {
            total = get_number_value();
        } else if (token.isWord()) {
            if (!token.isExpr()) {
                String name = token.word();
                token = tokenizer.get();
                if (token.type == TokenType.SYMBOL && token.isSymbol("=")) {
                    token = tokenizer.get();
                    total = Expr.affectation(name, get_tree_expr());
                    total.value(table);
                } else if(table.contains(name)){
                    total = Expr.variable(name);
                } else {
                    throw new SyntaxErrorException(String.format("variable %s does not exist", name));
                }
            } else {
                String name = token.word();
                token = tokenizer.get();
                checkSyntax(token.isSymbol("("), "( expected");
                token = tokenizer.get();
                total = apply_expr(name);
                checkSyntax(token.isSymbol(")"), ") expected");
                token = tokenizer.get();
            }
        } else if (token.isSymbol("(")) {
            token = tokenizer.get();
            total = get_tree_expr();
            checkSyntax(token.isSymbol(")"), ") expected");
            token = tokenizer.get();
        } else if (token.isSymbol("-")) {
            token = tokenizer.get();
            total = Expr.binary(get_tree_expr(), OpBinaire.MULTIPLY, Expr.constant(-1));
        } else {
            throw new SyntaxErrorException("SyntaxErrorException");
        }
        return total;
    }

    private Expr apply_expr(String name) throws SyntaxErrorException, EvaluationErrorException {
        Expr total = Expr.constant(0);
        switch (name) {
            case "abs":
                total = Expr.constant(Math.abs(get_tree_expr().value(table)));
                break;
            case "cos":
                total = Expr.constant(Math.cos(get_tree_expr().value(table)));
                break;
            case "sin":
                total = Expr.constant(Math.sin(get_tree_expr().value(table)));
                break;
            case "tan":
                total = Expr.constant(Math.tan(get_tree_expr().value(table)));
                break;
            case "sqrt":
                total = Expr.constant(Math.sqrt(get_tree_expr().value(table)));
                break;
        }
        return total;
    }

    /**
     * Returns the assembly instructions of the calculus
     * @param line the line to convert
     * @return the assembly instructions
     * @throws SyntaxErrorException catch input errors
     * @throws EvaluationErrorException catch calculation errors such as division by 0
     */
    public String compilation(String line) throws EvaluationErrorException, SyntaxErrorException {
        tokenizer = new Tokenizer(line.replaceAll(" +", ""));
        token = tokenizer.get();
        Expr total = get_tree_expr();
        return total.getCode();
    }
}
