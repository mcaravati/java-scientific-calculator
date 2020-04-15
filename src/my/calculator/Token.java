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
public class Token {
    final String SYMBOLS = "+-*/";

    final TokenType type;
    final String string;

    public Token(TokenType type, String string) {
        this.type = type;
        this.string = string;
    }

    public boolean isNumber() {
        return type == TokenType.NUMBER;
    }

    public boolean isEnd() {
        return type == TokenType.END;
    }
    
    public boolean isSymbol(String line) {
        return string.equals(line);
    }
    
    public boolean isASymbol() {
        return SYMBOLS.contains(string);
    }
    
    int value() {
        return Integer.parseInt(string);
    }
}