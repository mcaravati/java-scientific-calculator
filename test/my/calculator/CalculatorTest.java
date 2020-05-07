/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.calculator;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author owl
 */
public class CalculatorTest {

    @Test
    public void sumTest() throws SyntaxErrorException, EvaluationErrorException {
        Calculator calc = new Calculator();
        assertEquals(123, (int)calc.evaluation("   123   "));
        assertEquals(4, (int)calc.evaluation("2+2"));
        assertEquals(1, (int)calc.evaluation("421-20-400"));
        assertEquals(8, (int)calc.evaluation("2+2*3"));
    }

    @Test
    public void testDivisionZero() throws SyntaxErrorException {
        Calculator calc = new Calculator();
        try {
            calc.evaluation("1 + 2/0 + 3");
            fail();
        } catch (EvaluationErrorException ex) {

        }
    }

    @Test
    public void priorityMultiplicityTest() throws SyntaxErrorException, EvaluationErrorException {
        Calculator calc = new Calculator();
        assertEquals(150, (int)calc.evaluation(" 100+25*2 "));
        assertEquals(201, (int)calc.evaluation("100*2+1"));
        assertEquals(3, (int)calc.evaluation("1*1*1*2+1"));
    }

    @Test
    public void priorityTest() throws SyntaxErrorException, EvaluationErrorException {
        Calculator calc = new Calculator();
        assertEquals(4, (int)calc.evaluation("2 * ( 1 + 1 )"));
        assertEquals(7, (int)calc.evaluation("2 + ( 3 + 2 )"));
        assertEquals(50, (int)calc.evaluation("2 * ( 13 + 12 )"));
        assertEquals(70, (int)calc.evaluation("(2 * ( 30 )  ) + 10"));
        assertEquals(121, (int)calc.evaluation("2*3 + 4*5*6 - 10/2"));
    }

    @Test
    public void testAssignments() throws EvaluationErrorException, SyntaxErrorException {
        Calculator calc = new Calculator();
        assertEquals(12, (int)calc.evaluation("num = 3*4"));
        assertEquals(12, (int)calc.evaluation("num"));
        assertEquals(2, (int)calc.evaluation("den = 2"));
        assertEquals(2, (int)calc.evaluation("den"));
        assertEquals(6, (int)calc.evaluation("num / den"));

        assertEquals(10, (int)calc.evaluation("(a = 2+1) + (b = 2*3 + 1)"));
        assertEquals(3, (int)calc.evaluation("a"));
        assertEquals(7, (int)calc.evaluation("b"));
    }

}
