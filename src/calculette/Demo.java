package calculette;

import java.util.Scanner;
import my.calculator.*;

/**
 * Boucle principale d'interaction avec le programme
 * @author Matteo CARAVATI
 */
public class Demo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner in = new Scanner(System.in);
        System.out.println("Essai de boucle d'interaction");
        while (true) {
            System.out.print("> ");
            if (!in.hasNextLine()) {
                break;
            }
            String line = in.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.equals("\\q")) {
                break;
            }
            try {
                double value = calculator.evaluation(line.replaceAll(" +", ""));
                System.out.format("= %1$,.2f\n", value);
            }
            catch (SyntaxErrorException ex) {
                System.err.format("! Incorrect syntax : %s\n", ex.getMessage());
            }
            catch(EvaluationErrorException ex) {
                System.err.format("! Evaluation failed : %s\n", ex.getMessage());
            }
            catch (NumberFormatException ex) {
                System.err.format("! Number too big for a double : %s\n", ex.getMessage());
            }

        }
        System.out.println("Bye.");
    }

}
