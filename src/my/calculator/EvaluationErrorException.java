package my.calculator;

/**
 * Error thrown when there is an arithmetic error such as a division by 0
 * @author Matteo CARAVATI
 */
public class EvaluationErrorException extends Exception{ 

    /**
     * Throws an error with the message passed as argument
     * @param message The error message
     */
    public EvaluationErrorException(String message) {
        super(message);
    }
}
