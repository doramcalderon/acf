
package es.uma.pfc.implications.generator;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class NodesFactoryTest {
   

    /**
     * Comprueba que el método {@link NodesFactory#getNumbersArray(java.lang.Integer) } devuelve un array de números
     * enteros del tamaño pasado como parámetro.
     */
    @Test
    public void testGetNumbersArray() {
        Integer size = 5;
        String[] expectArray = new String[]{"0", "1", "2", "3", "4"};
        String[] array = AttributesFactory.get().getNumbersArray(size);
        assertArrayEquals(expectArray, array);
    }

     /**
     * Comprueba que el método {@link NodesFactory#getLettersArray(java.lang.Integer)  } devuelve un array de letras
     * del tamaño pasado como parámetro.
     */
    @Test
    public void testGetLettersArray() {
        Integer size = 4;
        String[] expectedArray = new String[]{"a", "b", "c", "d"};
        String[] array = AttributesFactory.get().getLettersArray(size);
        assertArrayEquals(expectedArray, array);
    }
     /**
     * Comprueba que el método {@link NodesFactory#getLettersArray(java.lang.Integer)  } devuelve un array de letras
     * del tamaño pasado como parámetro.
     */
    @Test
    public void testGetLettersArray_LettersDuplicated() {
        Integer size = 28;
        
        String[] expectedArray = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
                                              "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "a", "b"};
        String[] array = AttributesFactory.get().getLettersArray(size);
        assertArrayEquals(expectedArray, array);
    }

    /**
     * Comprueba que el método {@link NodesFactory#getIndexedLettersArray(java.lang.String, java.lang.Integer) } devuelve 
     * un array de letras indexadas del tamaño pasado como parámetro.
     */
    @Test
    public void testGetIndexedLettersArray() {
        String letter = "x";
        Integer size = 4;
        String[] expectedArray = new String[]{"x0", "x1", "x2", "x3"};
        String[] array = AttributesFactory.get().getIndexedLettersArray(letter, size);
        assertArrayEquals(expectedArray, array);
    }

    /**
     * Comprueba que el método {@link NodesFactory#getIndexedLettersArray(java.lang.String, java.lang.Integer) } devuelve 
     * un array de letras indexadas del tamaño pasado como parámetro.
     */
    @Test
    public void testGetIndexedLettersArrayDefaultLetter() {
        String letter = null;
        Integer size = 4;
        String[] expectedArray = new String[]{"a0", "a1", "a2", "a3"};
        String[] array = AttributesFactory.get().getIndexedLettersArray(letter, size);
        assertArrayEquals(expectedArray, array);
    }
    
}
