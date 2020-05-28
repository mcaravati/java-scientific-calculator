package my.calculator;

import java.util.Arrays;

/**
 * Class used to parse the user input
 * @author Matteo CARAVATI
 */
public class Token {
    final String SYMBOLS = "+-*/=^%";
    final String PRIORITY = "*/^%";
    final String[] expr = {"abs", "cos", "sin", "tan", "sqrt"};

    final TokenType type;
    final String string;

    /**
     * Creates a new Token object
     * @param type The token's type
     * @param string The token's value
     */
    public Token(TokenType type, String string) {
        this.type = type;
        this.string = string;
    }

    /**
     * Checks if the token contains a number
     * @return True if the token contains a number
     */
    public boolean isNumber() {
        return type == TokenType.NUMBER;
    }

    /**
     * Checks if the token contains the end of the line
     * @return True if the token contains the end of the line
     */
    public boolean isEnd() {
        return type == TokenType.END;
    }
    
    /**
     * Checks if the token contains a specific symbol
     * @param line The symbol to check
     * @return True if the token contains the given symbol
     */
    public boolean isSymbol(String line) {
        return string.equals(line);
    }
    
    /**
     * Checks if the token contains a symbol
     * @return True if the token contains a symbol
     */
    public boolean isSymbol() {
        return SYMBOLS.contains(string);
    }
    
    /**
     * Checks if the token contains a priority symbol such as *\/%^
     * @return True if the token contains a priority symbol
     */
    public boolean isPrioritySymbol() {
        return PRIORITY.contains(string);
    }
    
    int value() {
        return Integer.parseInt(string);
    }

    /**
     * Checks if the token contains a word
     * @return True if the token contains a word
     */
    public boolean isWord() {
        return type == TokenType.WORD;
    }
    
    /**
     * Gets the token word
     * @return The word
     */
    public String word() {
        return string;
    }
    
    /**
     * Checks if the token contains an Expr
     * @return True if the token contains an Expr
     */
    public boolean isExpr() {
        return Arrays.asList(expr).contains(string);
    }
}
