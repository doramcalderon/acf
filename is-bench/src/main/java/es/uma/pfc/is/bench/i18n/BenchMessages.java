/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench.i18n;

import es.uma.pfc.is.commons.i18n.Messages;
import java.util.ResourceBundle;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class BenchMessages extends Messages {
    /**
    * Empty values validation message.
    */
    public static final String EMPTY_VALUES = "msg.validation.empty.values";
    /**
     * Algorithm exists validation message.
     */
    public static final String ALGORITHM_EXISTS = "msg.algorithm.exists";
            
            
/**
     * Single instance.*
     */
    private static BenchMessages me;
    /**
     * Bundle.
     */
    protected final ResourceBundle messages;
    

    /**
     * Constructor.
     */
    private BenchMessages() {
        messages = ResourceBundle.getBundle("bundles.messages");
    }
    
    public static BenchMessages get() {
        if(me == null) {
            me = new BenchMessages();
        }
        return me;
    }
    
    @Override
    public ResourceBundle getBundle() {
        return messages;
    }

}