package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import es.uma.pfc.is.logging.AlgorithmLogger;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Algoritmo genérico que recibe como entrada una ruta de un fichero y como salida un sistema implicacional.
 *
 * @author Dora Calderón
 */
public abstract class GenericAlgorithm implements Algorithm {

    private AlgorithmLogger logger;
    
    private String name;
    private String shortName;
    protected AlgMessages messages;

    /**
     * Constructor.
     */
    public GenericAlgorithm() {
        logger = new AlgorithmLogger(getClass().getName(), new AlgorithmOptions());
        messages = AlgMessages.get();
    }
    

    @Override
    public ImplicationalSystem execute(String inputPath, AlgorithmOptions options) {
        ImplicationalSystem output = null;
        try {
            logger.setOptions(options);
            ImplicationalSystem input = new ImplicationalSystem(inputPath);
            output = execute(input);
        } catch (IOException ex) {
            Logger.getLogger(GenericAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    
    /**
     * Logger.
     * @return Logger. 
     */
    protected AlgorithmLogger getLogger() {
        return logger;
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    @Override
    public final void setName(String name) {
        this.name = name;
    }

    @Override
    public String getShortName() {
        return shortName;
    }

    @Override
    public final void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    
    @Override
    public String getDefaultOutputFileName() {
        return getShortName() + "_output.txt";
    }

/**
     * For testing usage.
     * @param logger Logger.
     */
    protected void setLogger(AlgorithmLogger logger) {
        this.logger = logger;
    }
    

    

    /**
     * Removes rules for implicational system, and print trace in the history.
     * @param system Implicational system.
     * @param rule Rule.
     * @param modes Mode.
     */
    protected void removeRule(ImplicationalSystem system, Rule rule) {
        if(system != null && rule != null) {
            String message = messages.getMessage(AlgMessages.REMOVE_RULE, rule);
            getLogger().history(message);
            
            system.removeRule(rule);
        }
    }
    
    
    /**
     * Adds rules for implicational system, and print trace in the history.
     * @param system Implicational system.
     * @param rule Rule.
     * @param modes Mode.
     */
    protected void addRule(ImplicationalSystem system, Rule rule) {
        if(system != null && rule != null) {
            getLogger().history(messages.getMessage(AlgMessages.REMOVE_RULE, rule));
            system.addRule(rule);
        }
    }
    
    /**
     * Adds rules for implicational system, and print trace in the history.
     * @param system Implicational system.
     * @param rule Rule.
     * @param modes Mode.
     */
    protected ImplicationalSystem addRuleAndElements(ImplicationalSystem system, Rule rule) {
        return addRuleAndElements(system, rule, true);
    }
    
    /**
     * Adds rules for implicational system, and print trace in the history.
     * @param system Implicational system.
     * @param rule Rule.
     * @param modes Mode.
     */
    protected ImplicationalSystem addRuleAndElements(ImplicationalSystem system, Rule rule, boolean trace) {

        if(system != null && rule != null) {
            if(system.containsRule(rule)) {
                if(trace) {
                    getLogger().history("Rule {} not added because system contains it.", rule);
                }
            } else {
                if(trace) {
                    getLogger().history(messages.getMessage(AlgMessages.ADD_RULE, rule));
                }
                ImplicationalSystems.addRuleAndElements(system, rule);
                
            }
        }
        return system;
    }
    
    /**
     * Replace a rule by other for implicational system, and print trace in the history.
     * @param system Implicational system.
     * @param rule1 Rule to replace.
     * @param rule2 New rule.
     */
    protected void replaceRule(ImplicationalSystem system, Rule rule1, Rule rule2) {
        if(system != null && rule1 != null && rule2 != null && !rule1.equals(rule2)) {
            getLogger().history(messages.getMessage(AlgMessages.REPLACE_RULE, rule1, rule2));
            system.replaceRule(rule1, rule2);
        }
    }
    
    protected void history(String message, Object ... args) {
        getLogger().history(message, args);
    }

    @Override
    public String toString() {
        return getName();
    }
    
    
}
