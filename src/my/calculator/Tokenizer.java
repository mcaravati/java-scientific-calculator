/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.calculator;

import java.util.function.Supplier;

/**
 *
 * @author owl
 */
public class Tokenizer implements Supplier<Token> {

    final String SYMBOLS = "+-*/=";

    String line;
    int next;

    public Tokenizer(String line) {
        this.line = line;
        this.next = 0;
    }

    @Override
    public Token get() {
        // ignorer les espaces
        while (next < line.length() && Character.isSpaceChar(line.charAt(next))) {
            next++;
        }
        // savoir si c'est la fin de la ligne
        if (next >= line.length()) {
            return new Token(TokenType.END, "");
        }

        char first = line.charAt(next);
        if (Character.isDigit(first)) {
            return getNumber();
        } else if (SYMBOLS.indexOf(first) >= 0) {
            return getSymbol();
        } else if (Character.isLetter(first)) {
            return getWord();
        } else {
            next++;
            return new Token(TokenType.INVALID, Character.toString(first));
        }
    }

    private Token getNumber() {
        StringBuilder builder = new StringBuilder();
        do {
            builder.append(line.charAt(next));
            next++;
        } while (next < line.length() && Character.isDigit(line.charAt(next)));
        return new Token(TokenType.NUMBER, builder.toString());
    }

    private Token getSymbol() {
        String string = Character.toString(line.charAt(next));
        next++;
        return new Token(TokenType.SYMBOL, string);
    }

    
    private Token getWord() {
        String string = "";
        while(next < line.length() && (Character.isLetter(line.charAt(next)) || Character.toString(line.charAt(next)).equals("_"))) {
            string = string.concat(Character.toString(line.charAt(next)));
            next++;
        }
        
        return new Token(TokenType.WORD, string);
    }
}
