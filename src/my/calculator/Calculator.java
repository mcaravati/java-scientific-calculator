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
    Map<String, Integer> table = new HashMap<>();

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
        } else if (token.isWord()) {
            String name = token.word();
            token = tokenizer.get();
            if (token.type == TokenType.SYMBOL && token.isSymbol("=")) {
                assignWord(name);
            }
            if(!table.containsKey(name)) {
                throw new EvaluationErrorException("Variable \"" + name + "\" undefined");
            }
            total = table.get(name);
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

    private int assignWord(String name) throws SyntaxErrorException, EvaluationErrorException {
        int value = 0;
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
