/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.calculator;

/**
 *
 * @author owl
 */
public class Calculator {

    public Calculator() {
    }

    public int evaluation(String line) throws SyntaxErrorException, EvaluationErrorException {
        Tokenizer tokenizer = new Tokenizer(line);
        Token token = tokenizer.get();
        Token token2 = null;
        checkSyntax(token.isNumber(), "Number expected");
        int total = token.value();

        token = tokenizer.get();
        while (!token.isEnd()) {
            if (token.isASymbol()) {
                token2 = tokenizer.get();
                checkSyntax(token2.isNumber(), "Number expected");
                switch (token.string) {
                    case "+":
                        total += token2.value();
                        break;
                    case "-":
                        total -= token2.value();
                        break;
                    case "/":
                        if (token2.value() == 0) {
                            throw new EvaluationErrorException(token2.string);
                        }
                        total /= token2.value();
                        break;
                }
                token = tokenizer.get();
            }
        }

        checkSyntax(token.isEnd(), String.format("End of expression expected, %s found", token));
        return total;
    }

    private void checkSyntax(boolean state, String line) throws SyntaxErrorException {
        if (!state) throw new SyntaxErrorException(line);
    }
}
