/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculette;

import java.util.Scanner;
import my.calculator.*;

/**
 *
 * @author owl
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
                int value = calculator.evaluation(line.replaceAll(" +", ""));
                System.out.format("> %d\n", value);
            }
            catch (SyntaxErrorException ex) {
                System.out.format("! Incorrect syntax %s\n", ex.getMessage());
            }
            catch(EvaluationErrorException ex) {
                System.out.format("! Evaluation failed %s\n", ex.getMessage());
            }

        }
        System.out.println("Bye.");
    }

}
