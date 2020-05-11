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
    TableVariables table = new TableVariables();

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

        Expr total = get_expr_value();

        checkSyntax(token.isEnd(), String.format("End of expression expected, %s found", token));
        return total.valeur(table);
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

    private Expr get_expr_value() throws SyntaxErrorException, EvaluationErrorException {
        Expr total = get_term_value();
        Expr tmp;
        while (token.isSymbol() && !token.isEnd()) {
            switch (token.string) {
                case "+":
                    token = tokenizer.get();
                    tmp = get_term_value();
                    total = Expr.binaire(total, OpBinaire.PLUS, tmp);
                    break;
                case "-":
                    token = tokenizer.get();
                    tmp = get_term_value();
                    total = Expr.binaire(total, OpBinaire.MOINS, tmp);
                    break;
            }

        }
        return total;
    }

    private Expr get_term_value() throws SyntaxErrorException, EvaluationErrorException {
        Expr total = get_factor_value();
        Expr tmp;
        while (token.isPrioritySymbol() && !token.isEnd()) {
            switch (token.string) {
                case "/":
                    token = tokenizer.get();
                    if (token.isNumber() && token.value() == 0) {
                        throw new EvaluationErrorException("Division by 0");
                    }
                    tmp = get_factor_value();
                    total = Expr.binaire(total, OpBinaire.DIVISE, tmp);
                    break;
                case "*":
                    token = tokenizer.get();
                    tmp = get_factor_value();
                    total = Expr.binaire(total, OpBinaire.FOIS, tmp);
                    break;
                case "^":
                    token = tokenizer.get();
                    total = Expr.constante(Math.pow(total.valeur(table), get_factor_value().valeur(table)));
                    break;
                case "%":
                    token = tokenizer.get();
                    Expr tok = get_factor_value();
                    total = Expr.binaire(
                            total,
                            OpBinaire.MOINS,
                            Expr.binaire(
                            tok,
                            OpBinaire.FOIS,
                            Expr.binaire(total,
                                    OpBinaire.DIVISE,
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
        return Expr.constante(value);
    }

    private Expr get_factor_value() throws SyntaxErrorException, EvaluationErrorException {
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
                if (!table.contient(name)) {
                    throw new EvaluationErrorException("Variable or expression \"" + name + "\" undefined");
                }
                total = Expr.constante(table.valeur(name));
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
            total = get_expr_value();
            checkSyntax(token.isSymbol(")"), ") expected");
            token = tokenizer.get();
        } else if (token.isSymbol("-")) {
            token = tokenizer.get();
            total = get_expr_value();
        } else {
            throw new SyntaxErrorException("What the fuck");
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
        Expr total = Expr.constante(0);
        switch (name) {
            case "abs":
                total = Expr.constante(Math.abs(get_expr_value().valeur(table)));
                break;
            case "cos":
                total = Expr.constante(Math.cos(get_expr_value().valeur(table)));
                break;
            case "sin":
                total = Expr.constante(Math.sin(get_expr_value().valeur(table)));
                break;
            case "tan":
                total = Expr.constante(Math.tan(get_expr_value().valeur(table)));
                break;
            case "sqrt":
                total = Expr.constante(Math.sqrt(get_expr_value().valeur(table)));
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
        Expr value = Expr.constante(0);
        token = tokenizer.get();
        if (token.type == TokenType.WORD) {
            String newname = token.word();
            token = tokenizer.get();
            value = assignWord(newname);
        } else if (token.type == TokenType.NUMBER) {
            value = get_expr_value();
        }
        table.affecter(name, value.valeur(table));
        return value;
    }
}
