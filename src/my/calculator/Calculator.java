/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.calculator;

import java.lang.Math;
import java.util.HashMap;
import java.util.Map;
import my.calculator.expr.Expr;
import my.calculator.expr.OpBinaire;

/**
 *
 * @author owl
 */
public class Calculator {

    Tokenizer tokenizer;
    Token token;
    VariablesTable table = new VariablesTable();

    public Calculator() {

    }

    /**
     * Calculates the value of the inserted expression
     * @param line The user's input
     * @return The computed value
     * @throws SyntaxErrorException
     * @throws EvaluationErrorException
     */
    public double evaluation(String line) throws SyntaxErrorException, EvaluationErrorException {
        tokenizer = new Tokenizer(line.replaceAll(" +", ""));
        token = tokenizer.get();

        Expr total = get_tree_expr();

        checkSyntax(token.isEnd(), String.format("End of expression expected, %s found", token));
        return total.value(table);
    }

    /**
     * Check if syntax is correct, raises an error if not
     * @param state The state to check
     * @param line The returned error message
     * @throws SyntaxErrorException 
     */
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
                            OpBinaire.MINUS,
                            Expr.binary(
                            tok,
                            OpBinaire.MULTIPLY,
                            Expr.binary(total,
                                    OpBinaire.DIVIDE,
                                    tok)
                            ));
                    /*int tmp = ((int) total / (int) tok);
                    total = total - (tok * tmp);*/
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
        Expr total;
        if (token.isNumber()) {
            total = get_number_value();
        } else if (token.isWord()) {
            if (!token.isExpr()) {
                String name = token.word();
                token = tokenizer.get();
                if (token.type == TokenType.SYMBOL && token.isSymbol("=")) {
                    assignWord(name);
                }
                if (!table.contains(name)) {
                    throw new EvaluationErrorException("Variable or expression \"" + name + "\" undefined");
                }
                total = Expr.constant(table.value(name));
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
            total = Expr.constant(get_tree_expr().value(table) * -1);
        } else {
            throw new SyntaxErrorException("SyntaxErrorException");
        }
        return total;
    }

    /**
     * Applies a mathematical expression to a value
     * @param name The expression's name
     * @return The computed value
     * @throws SyntaxErrorException
     * @throws EvaluationErrorException 
     */
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
     * Recursively assign a value to one or multiple variables
     * @param name The variable name
     * @return The assignated value
     * @throws SyntaxErrorException
     * @throws EvaluationErrorException 
     */
    private Expr assignWord(String name) throws SyntaxErrorException, EvaluationErrorException {
        Expr value = Expr.constant(0);
        token = tokenizer.get();
        if (token.type == TokenType.WORD) {
            String newname = token.word();
            token = tokenizer.get();
            value = assignWord(newname);
        } else if (token.type == TokenType.NUMBER) {
            value = get_tree_expr();
        }
        table.apply(name, value.value(table));
        return value;
    }
}
