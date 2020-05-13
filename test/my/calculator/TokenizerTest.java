package my.calculator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TokenizerTest {

    @Test
    public void testEnd() {
        String line = "    ";
        Tokenizer tokenizer = new Tokenizer(line);

        Token token = tokenizer.get();
        assertTrue(token.isEnd());
        // Confusion detection
        assertFalse(token.isNumber());
        assertFalse(token.isSymbol("+"));
    }

    @Test
    public void testNumber() {
        String line = " 123   ";
        Tokenizer tokenizer = new Tokenizer(line);

        Token token = tokenizer.get();
        assertTrue(token.isNumber());
        assertEquals(123, token.value());
        assertFalse(token.isEnd());
        assertFalse(token.isSymbol("+"));
    }

    @Test
    public void testSymbol() {
        String line = " *   ";
        Tokenizer tokenizer = new Tokenizer(line);

        Token token = tokenizer.get();
        assertTrue(token.isSymbol("*"));
        assertFalse(token.isNumber());
        assertFalse(token.isEnd());
    }

    @Test
    public void testSequence() {
        String line = "12+(34)";
        Tokenizer tokenizer = new Tokenizer(line);

        Token token = tokenizer.get();
        assertTrue(token.isNumber());
        assertEquals(12, token.value());

        token = tokenizer.get();
        assertTrue(token.isSymbol("+"));

        token = tokenizer.get();
        assertTrue(token.isSymbol("("));

        token = tokenizer.get();
        assertTrue(token.isNumber());
        assertEquals(34, token.value());

        token = tokenizer.get();
        assertTrue(token.isSymbol(")"));

        token = tokenizer.get();
        assertTrue(token.isEnd());

    }
    
    @Test
    public void testWord() {
        String line = " hello  ";
        Tokenizer tokenizer = new Tokenizer(line);

        Token token = tokenizer.get();
        assertTrue(token.isWord());
        assertEquals("hello", token.word());
        
        // etc.
    }
    
    @Test
    public void testSequenceWords() {
        // To check if the next wrong character isn't taken
        String line = "abc=def";
        Tokenizer tokenizer = new Tokenizer(line);

        Token token = tokenizer.get();
        assertTrue(token.isWord());
        assertEquals("abc", token.word());

        token = tokenizer.get();
        assertTrue(token.isSymbol("="));
        
        token = tokenizer.get();
        assertTrue(token.isWord());
        assertEquals("def", token.word());
        
        token = tokenizer.get();
        assertTrue(token.isEnd());
    }
}