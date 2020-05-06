/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.calculator;

import java.lang.Math;
/**
 *
 * @author owl
 */
public class Calculator {

    Tokenizer tokenizer;
    Token token;

    public Calculator() {

    }

    public int evaluation(String line) throws SyntaxErrorException, EvaluationErrorException {
        tokenizer = new Tokenizer(line.replaceAll(" +", ""));
        token = tokenizer.get();

        int total = get_expr_value();

        checkSyntax(token.isEnd(), String.format("End of expression expected, %s found", token));
        return total;
    }

    private void checkSyntax(boolean state, String line) throws SyntaxErrorException {
        if (!state) {
            throw new SyntaxErrorException(line);
        }
    }

    private int get_expr_value() throws SyntaxErrorException, EvaluationErrorException {
        int total = get_term_value();

        while (token.isSymbol() && !token.isEnd()) {
            switch (token.string) {
                case "+":
                    token = tokenizer.get();
                    total += get_term_value();
                    break;
                case "-":
                    token = tokenizer.get();
                    total -= get_term_value();
                    break;
            }

        }
        return total;
    }

    private int get_term_value() throws SyntaxErrorException, EvaluationErrorException {
        int total = get_factor_value();

        while (token.isPrioritySymbol() && !token.isEnd()) {
            switch (token.string) {
                case "/":
                    token = tokenizer.get();
                    if (token.isNumber() && token.value() == 0) {
                        throw new EvaluationErrorException("Division by 0");
                    }
                    total /= get_factor_value();
                    break;
                case "*":
                    token = tokenizer.get();
                    total *= get_factor_value();
                    break;
            }

        }
        return total;
    }

    private int get_number_value() throws SyntaxErrorException {
        checkSyntax(token.isNumber(), "Number expected");
        int value = token.value();
        token = tokenizer.get();
        return value;
    }

    private int get_factor_value() throws SyntaxErrorException, EvaluationErrorException {
        int total = 0;
        if (token.isNumber()) {
            total += get_number_value();
        } else if (token.isSymbol("(")) {
            token = tokenizer.get();
            total += get_expr_value();
            checkSyntax(token.isSymbol(")"), ") expected");
            token = tokenizer.get(); 
        } else {
            throw new SyntaxErrorException("What the fuck");
        }
        return total;
    }
}
