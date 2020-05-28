package my.calculator;

import my.calculator.expr.Expr;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for the Calculator class
 *
 * @author Matteo CARAVATI
 */
public class CalculatorTest {

    /**
     * Tries some basic calculations
     *
     * @throws SyntaxErrorException catch input errors
     * @throws EvaluationErrorException catch calculation errors such as
     * division by 0
     */
    @Test
    public void calculationTest() throws SyntaxErrorException, EvaluationErrorException {
        Calculator calc = new Calculator();
        assertEquals(123, (int) calc.evaluation("   123   "));
        assertEquals(4, (int) calc.evaluation("2+2"));
        assertEquals(1, (int) calc.evaluation("421-20-400"));
        assertEquals(8, (int) calc.evaluation("2+2*3"));
    }

    /**
     * Tries to divide by 0, and see if the exception is throwed
     *
     * @throws SyntaxErrorException catch calculation errors such as division by
     * 0
     */
    @Test
    public void testDivisionZero() throws SyntaxErrorException {
        Calculator calc = new Calculator();
        try {
            calc.evaluation("1 + 2/0 + 3");
            fail();
        } catch (EvaluationErrorException ex) {

        }
    }

    /**
     * Tries to do some multiplications
     *
     * @throws SyntaxErrorException catch input errors
     * @throws EvaluationErrorException catch calculation errors such as
     * division by 0
     */
    @Test
    public void priorityMultiplicityTest() throws SyntaxErrorException, EvaluationErrorException {
        Calculator calc = new Calculator();
        assertEquals(150, (int) calc.evaluation(" 100+25*2 "));
        assertEquals(201, (int) calc.evaluation("100*2+1"));
        assertEquals(3, (int) calc.evaluation("1*1*1*2+1"));
    }

    /**
     * Tries to do some calculations with priority
     *
     * @throws SyntaxErrorException catch input errors
     * @throws EvaluationErrorException catch calculation errors such as
     * division by 0
     */
    @Test
    public void priorityTest() throws SyntaxErrorException, EvaluationErrorException {
        Calculator calc = new Calculator();
        assertEquals(4, (int) calc.evaluation("2 * ( 1 + 1 )"));
        assertEquals(7, (int) calc.evaluation("2 + ( 3 + 2 )"));
        assertEquals(50, (int) calc.evaluation("2 * ( 13 + 12 )"));
        assertEquals(70, (int) calc.evaluation("(2 * ( 30 )  ) + 10"));
        assertEquals(121, (int) calc.evaluation("2*3 + 4*5*6 - 10/2"));
    }

    /**
     * Tries to do calculations ith assigned variables
     *
     * @throws SyntaxErrorException catch input errors
     * @throws EvaluationErrorException catch calculation errors such as
     * division by 0
     */
    @Test
    public void testAssignments() throws EvaluationErrorException, SyntaxErrorException {
        Calculator calc = new Calculator();
        assertEquals(12, (int) calc.evaluation("num = 3*4"));
        assertEquals(12, (int) calc.evaluation("num"));
        assertEquals(2, (int) calc.evaluation("den = 2"));
        assertEquals(2, (int) calc.evaluation("den"));
        assertEquals(6, (int) calc.evaluation("num / den"));

        assertEquals(10, (int) calc.evaluation("(a = 2+1) + (b = 2*3 + 1)"));
        assertEquals(3, (int) calc.evaluation("a"));
        assertEquals(7, (int) calc.evaluation("b"));
    }

    /**
     * Tries to compile some different expressions
     * @throws SyntaxErrorException catch input errors
     * @throws EvaluationErrorException catch calculation errors such as
     * division by 0
     */
    @Test
    public void testCompilation() throws EvaluationErrorException, SyntaxErrorException {
        Calculator calc = new Calculator();
        assertEquals("li    r1, 123,000000", calc.compilation("123"));
        assertEquals("li    r1, 2,000000\n"
                + "li    r2, 2,000000\n"
                + "add    r1, r2", calc.compilation("2+2"));
        assertEquals("li    r1, 2,000000\n"
                + "li    r2, 1,000000\n"
                + "add    r1, r2\n"
                + "st    r1, a\n"
                + "li    r2, 2,000000\n"
                + "li    r3, 3,000000\n"
                + "mul    r2, r3\n"
                + "li    r3, 1,000000\n"
                + "add    r2, r3\n"
                + "st    r2, b\n"
                + "add    r1, r2", calc.compilation("(a = 2+1) + (b = 2*3 + 1)"));
    }

}
