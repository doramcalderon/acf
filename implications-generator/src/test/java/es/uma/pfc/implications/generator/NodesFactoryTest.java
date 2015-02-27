/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.implications.generator;

import es.uma.pfc.implications.generator.model.NodeType;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class NodesFactoryTest {
    
    public NodesFactoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Comprueba que el método {@link NodesFactory#getNumbersArray(java.lang.Integer) } devuelve un array de números
     * enteros del tamaño pasado como parámetro.
     */
    @Test
    public void testGetNumbersArray() {
        Integer size = 5;
        String[] expectArray = new String[]{"0", "1", "2", "3", "4"};
        String[] array = NodesFactory.get().getNumbersArray(size);
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
        String[] array = NodesFactory.get().getLettersArray(size);
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
        String[] array = NodesFactory.get().getLettersArray(size);
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
        String[] array = NodesFactory.get().getIndexedLettersArray(letter, size);
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
        String[] array = NodesFactory.get().getIndexedLettersArray(letter, size);
        assertArrayEquals(expectedArray, array);
    }
    
}
