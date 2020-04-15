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
    public void sumTest() throws SyntaxErrorException,EvaluationErrorException{
        Calculator calc = new Calculator();
        assertEquals(123, calc.evaluation("   123   "));
        assertEquals(4, calc.evaluation("2+2"));
        assertEquals(1, calc.evaluation("421-20-400"));
        assertEquals(8, calc.evaluation("2+2*3"));
    }
    
    @Test
    public void testDivisionZero()throws SyntaxErrorException{
        Calculator calc = new Calculator();
        try {
            calc.evaluation("1 + 2/0 + 3");
            fail();
        } catch (EvaluationErrorException ex) {
            
        }
    }
    
}
