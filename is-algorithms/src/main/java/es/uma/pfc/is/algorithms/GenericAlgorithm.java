package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import es.uma.pfc.is.logging.AlgorithmLogger;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;

/**
 * Generic algorithm which receive as input a file input and returns an implicational system.
 *
 * @author Dora Calderón
 */
public abstract class GenericAlgorithm implements Algorithm {
    /**
     * Logger.
     */
    private AlgorithmLogger logger;
    
    /**
     * Name.
     */
    private String name;
    /**
     * Short name.
     */
    private String shortName;
    /**
     * I18n messages.
     */
    protected AlgMessages messages;

    /**
     * Constructor.
     */
    public GenericAlgorithm() {
        logger = new AlgorithmLogger(getClass().getName(), new AlgorithmOptions());
        messages = AlgMessages.get();
    }
    

    
    /**
     * Logger.
     * @return Logger. 
     */
    @Override
    public AlgorithmLogger getLogger() {
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
     * @return Implicational system.
     */
    protected ImplicationalSystem addRuleAndElements(ImplicationalSystem system, Rule rule) {
        return addRuleAndElements(system, rule, true);
    }
    
    /**
     * Adds rules for implicational system, and print trace in the history.
     * @param system Implicational system.
     * @param rule Rule.
     * @param trace
     * @return 
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
