/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.calculator;

import java.lang.Math;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author owl
 */
public class Calculator {

    Tokenizer tokenizer;
    Token token;
    Map<String, Double> table = new HashMap<>();

    public Calculator() {

    }

    public double evaluation(String line) throws SyntaxErrorException, EvaluationErrorException {
        tokenizer = new Tokenizer(line.replaceAll(" +", ""));
        token = tokenizer.get();

        double total = get_expr_value();

        checkSyntax(token.isEnd(), String.format("End of expression expected, %s found", token));
        return total;
    }

    private void checkSyntax(boolean state, String line) throws SyntaxErrorException {
        if (!state) {
            throw new SyntaxErrorException(line);
        }
    }

    private double get_expr_value() throws SyntaxErrorException, EvaluationErrorException {
        double total = get_term_value();

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

    private double get_term_value() throws SyntaxErrorException, EvaluationErrorException {
        double total = get_factor_value();

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
                case "^":
                    token = tokenizer.get();
                    total = Math.pow((double)total, (double)get_factor_value());
                    break;
                case "%":
                    token = tokenizer.get();
                    double tok = get_factor_value();
                    int tmp = ((int)total/(int)tok);
                    total = total - (tok*tmp);
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

    private double get_factor_value() throws SyntaxErrorException, EvaluationErrorException {
        double total = 0;
        if (token.isNumber()) {
            total += get_number_value();
        } else if (token.isWord()) {
            if (!token.isExpr()) {
                String name = token.word();
                token = tokenizer.get();
                if (token.type == TokenType.SYMBOL && token.isSymbol("=")) {
                    assignWord(name);
                }
                if (!table.containsKey(name)) {
                    throw new EvaluationErrorException("Variable \"" + name + "\" undefined");
                }
                total = table.get(name);
            } else {
                String name = token.word();
                token = tokenizer.get();
                checkSyntax(token.isSymbol("("), "( expected");
                token = tokenizer.get();
                total += apply_expr(name);
                checkSyntax(token.isSymbol(")"), ") expected");
                token = tokenizer.get();
            }
        } else if (token.isSymbol("(")) {
            token = tokenizer.get();
            total += get_expr_value();
            checkSyntax(token.isSymbol(")"), ") expected");
            token = tokenizer.get();
        } else if(token.isSymbol("-")) {
            token = tokenizer.get();
            total -= get_expr_value();
        } else {
            throw new SyntaxErrorException("What the fuck");
        }
        return total;
    }

    private int apply_expr(String name) throws SyntaxErrorException, EvaluationErrorException {
        int total = 0;
        switch(name) {
            case "abs":
                total += Math.abs(get_expr_value());
                break;
            case "cos":
                total += Math.cos((double)get_expr_value());
                break;
            case "sin":
                total += Math.sin((double)get_expr_value());
                break;
            case "tan":
                total += Math.tan((double)get_expr_value());
                break;
            case "sqrt":
                total += Math.sqrt((double)get_expr_value());
                break;
        }
        return total;
    }
    
    private double assignWord(String name) throws SyntaxErrorException, EvaluationErrorException {
        double value = 0;
        token = tokenizer.get();
        if (token.type == TokenType.WORD) {
            String newname = token.word();
            token = tokenizer.get();
            value = assignWord(newname);
        } else if (token.type == TokenType.NUMBER) {
            value = get_expr_value();
        }
        table.put(name, value);
        return value;
    }
}
