package my.calculator;

/**
 * Error thrown when the user enters a wrong input such as 4-
 * @author Matteo CARAVATI
 */
public class SyntaxErrorException extends Exception{

    /**
     * Throws an error with the message passed as argument
     * @param message The error message
     */
    public SyntaxErrorException(String message) {
        super(message);
    }
    
}
