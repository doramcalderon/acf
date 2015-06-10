/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.implications.generator;

import es.uma.pfc.implications.generator.i18n.I18n;
import com.google.common.base.Strings;
import es.uma.pfc.implications.generator.exception.ZeroNodesException;
import es.uma.pfc.implications.generator.i18n.GeneratorMessages;
import es.uma.pfc.implications.generator.model.AttributeType;
import es.uma.pfc.is.commons.i18n.Messages;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @since @author Dora Calderón
 */
public class AttributesFactory {

    static String characters = "abcdefghijklmnopqrstuvwxyz";
    private static AttributesFactory instance;
    private final Messages messages;

    public AttributesFactory() {
        messages = GeneratorMessages.get();
    }
    
    

    public static AttributesFactory get() {
        if (instance == null) {
            instance = new AttributesFactory();
        }
        return instance;
    }

    public String[] getAttributes(AttributeType type, Integer size) {
        checkAttributesSize(size);
        String[] nodes = null;

        if (type == null || AttributeType.NUMBER.equals(type)) {
            nodes = getNumbersArray(size);
        } else if (AttributeType.LETTER.equals(type)) {
            nodes = getLettersArray(size);
        } else if (AttributeType.INDEXED_LETTER.equals(type)) {
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
    protected void checkAttributesSize(Integer size) {
        if (size == null || size < 1) {
            throw new ZeroNodesException(messages.getMessage(I18n.INVALID_ATTRIBUTES_NUM));
        }
    }
}
