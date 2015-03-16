/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.implications.generator;

import com.google.common.base.Strings;
import es.uma.pfc.implications.generator.exception.ZeroNodesException;
import es.uma.pfc.implications.generator.model.NodeType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @since @author Dora Calderón
 */
public class NodesFactory {

    static String characters = "abcdefghijklmnopqrstuvwxyz";
    private static NodesFactory instance;

    public static NodesFactory get() {
        if (instance == null) {
            instance = new NodesFactory();
        }
        return instance;
    }

    public String[] getNodes(NodeType type, Integer size) {
        checkNodesSize(size);
        String[] nodes = null;

        if (type == null || NodeType.NUMBER.equals(type)) {
            nodes = getNumbersArray(size);
        } else if (NodeType.LETTER.equals(type)) {
            nodes = getLettersArray(size);
        } else if (NodeType.INDEXED_LETTER.equals(type)) {
            nodes = getIndexedLettersArray(null, size);
        }

        return nodes;
    }

 /**
     * Devuelve un array de números del número de elementos pasado como parámetro.
     *
     * @param size Tamaño.
     * @return Array de números.
     */
    protected String[] getNumbersArray(Integer size) {
        String[] numbers = new String[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = String.valueOf(i);
        }
        return numbers;
    }

    /**
     * Devuelve un array de letras del número de elementos pasado como parámetro.
     *
     * @param size Tamaño.
     * @return Array de letras.
     */
    protected String[] getLettersArray(Integer size) {
        List<String> letters = new ArrayList();

        while (letters.size() < size) {
            for (int i = 0; i < size; i++) {
                letters.add(String.valueOf(characters.charAt(i % characters.length())));
            }
        }
        return letters.toArray(new String[]{});
    }
    
     /**
     * Devuelve un array de letras indexadasdel número de elementos pasado como parámetro.
     *
     * @param size Tamaño.
     * @return Array de letras indexadas.
     */
    protected String[] getIndexedLettersArray(String letter, Integer size) {
        String[] lettersArray = new String[size];
        String letterAux = (Strings.isNullOrEmpty(letter)) ? "a" : letter;
        for(int i = 0; i < size; i++) {
            lettersArray[i] = letterAux.concat(String.valueOf(i));
        }
        return lettersArray;
    }

    /**
     * Comprueba que el tamaño sea mayor que 0.
     * @param size Tamaño.
     * @throws ZeroNodesException si el tamaño es menor que 0 o nulo.
     */
    protected void checkNodesSize(Integer size) {
        if (size == null || size < 1) {
            throw new ZeroNodesException();
        }
    }
}
