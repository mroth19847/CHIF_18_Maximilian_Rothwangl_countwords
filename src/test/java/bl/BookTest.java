/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import java.util.HashMap;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;

public class BookTest {
    
    public BookTest() {
    }
    
    @Test
    public void testCountWords() {
        System.out.println("Test of following method: countWords()");
        Book instance = new Book("", "This is a test. This is a test. #JUnitTests");
        HashMap<String, Integer> expResult = new HashMap<>();
        expResult.put("This", 2);
        expResult.put("is", 2);
        expResult.put("a", 2);
        expResult.put("test", 2);
        expResult.put("JUnitTests", 1);
        HashMap<String, Integer> result = instance.countWords();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetWordNumber() {
        System.out.println("Test of following method: getWordNumber()");
        String word = "test";
        String[] words = { "I", "am", "writing", "a", "unit", "test", "in", "order", "to", "test", "the", "function" };
        Book instance = new Book("", "");
        int expResult = 2;
        int result = instance.getWordNumber(word, words);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetFilteredText() {
        System.out.println("Test of following method: getFilteredText()");
        Book instance = new Book("", "This is a test. #unittests, they are sometimes useful!");
        String expResult = "This is a test unittests they are sometimes useful";
        String result = instance.getFilteredText();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetOutputFilename() {
        System.out.println("Test of following method: getOutputFilename()");
        Book instance = new Book("C:\\Documents\\text1.txt", "");
        String expResult = "text1_output.txt";
        String result = instance.getOutputFilename();
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
}
