package es.uma.pfc.implications.generator;


import es.uma.pfc.implications.generator.model.ImplicationsModel;
import es.uma.pfc.is.commons.io.ImplicationalSystemWriterProlog;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import fr.kbertet.util.ComparableSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Implicational systems factory.
 * @author Dora Calderón
 */
public class ImplicationsFactory {
    /**
     * Initializes the factory.
     */
    public static void initialize() {
        ImplicationalSystemWriterProlog.register();
    }
    /**
     * Generates a random system with the nodes and implications number passed by parameter.
     * @param nodesNumber Nodes number.
     * @param rulesNumber Implications number.
     * @return Implicational system.
     */
    public static ImplicationalSystem getImplicationalSystem(int nodesNumber, int rulesNumber) {
        return ImplicationalSystem.random(nodesNumber, rulesNumber);
    }


    /**
     * Returns n implicational systems fulfilling the established restrictions in the parameter
     * {@code implicationsModel}.
     *
     * @param implicationsModel Restrictions of the systems to generate.
     * @return Random implicational systems list.
     */
    public static List<ImplicationalSystem> getImplicationalSystems(ImplicationsModel implicationsModel) {
        List<ImplicationalSystem> systems = new ArrayList();
        Integer n = implicationsModel.getNum();
        
        if(n != null && n > 0) {
            for (int i = 0; i < n; i++) {
                systems.add(getImplicationalSystem(implicationsModel));
            }
            
        }
        
        return systems;
    }


    /**
     * Returns an implicational systems fulfilling the established restrictions in the parameter
     * {@code implicationsModel}.
     *
     * @param implicationsModel Restrictions of the systems to generate.
     * @return Random implicational system.
     */
    public static ImplicationalSystem getImplicationalSystem(ImplicationsModel implicationsModel) {
        ImplicationalSystem sigma = new ImplicationalSystem();
        
        Integer nodesNumber = implicationsModel.getNodes();
        Integer rulesNumber = implicationsModel.getImplications();
        Integer maxConclusionsLength = implicationsModel.getMaxConclusionLength();
        Integer minConclusionsLength = implicationsModel.getMinConclusionLength();
        Integer maxPremisesLength = implicationsModel.getMaxPremiseLength();
        Integer minPremisesLength = implicationsModel.getMinPremiseLength();

        String[] nodes = AttributesFactory.get().getAttributes(implicationsModel.getNodeType(), nodesNumber);
        // addition of elements
        for (int j = 0; j < nodesNumber; j++) {
            sigma.addElement(nodes[j]);
        }
        // addition of rules
        while (sigma.getRules().size() < rulesNumber) {
            ComparableSet conclusion = getConclusion(sigma, minConclusionsLength, maxConclusionsLength);
            ComparableSet premisse = getPremise(sigma, minPremisesLength, maxPremisesLength);
            sigma.addRule(new Rule(premisse, conclusion));
        }
        
        return sigma;
    }

    /**
     * Returns a conclusion with a max node number.If {@code maxLength} is null or less than 0, the max nodes number is th
     * system nodes number.
     * @param system Implicational system.
     * @param minLength Min nodes number.
     * @param maxLength Max nodes number.
     * @return Conclusions.
     * @throws RuntimeException if {@code maxLength} is greater than system attributes size.
     */
    protected static ComparableSet getConclusion(ImplicationalSystem system, Integer minLength, Integer maxLength) {
        ComparableSet conclusion = new ComparableSet();
        int maxIndex = system.getSet().size() - 1;
        if(maxLength != null && (maxLength > maxIndex)) {
            throw new RuntimeException("The length can't be greater than number or attributes.");
        }
        
        int minSize = (minLength != null && minLength > 0) ? minLength : 0;
        int maxSize = (maxLength != null && maxLength > 0) ? getRandomInt(minSize, maxLength) : getRandomInt(minSize, maxIndex);
        int choice;
        List nodesList = new ArrayList(system.getSet());
        
        
        
        while (conclusion.size() < maxSize) {
            choice = getRandomInt(maxIndex);
            conclusion.add(nodesList.get(choice));
        }
        return conclusion;
    }

    /**
     * Returns a premise with a max nodes number.<br/>
     * If {@code maxLength} is null or less than 0, the max nodes number is the system nodes number.
     * @param system Implicational system.
     * @param minLength Min nodes number.
     * @param maxLength Max nodes number.
     * @return Premisas.
     */
    protected static ComparableSet getPremise(ImplicationalSystem system, Integer minLength, Integer maxLength) {
        ComparableSet premisse = new ComparableSet();
        int maxIndex = system.getSet().size() - 1;
        int minSize = (minLength != null && minLength > 0) ? minLength : 0;
        int size = (maxLength != null && maxLength > 0) ? getRandomInt(minSize, maxLength) : getRandomInt(minSize, maxIndex);
        int choice;

        List nodesList = new ArrayList(system.getSet());
        while (premisse.size() < size) {
            choice = getRandomInt(maxIndex);
            premisse.add(nodesList.get(choice));
        }
        return premisse;
    }

    /**
     * Returns a random integer, between 0 and {@code maxValue}.
     *
     * @param maxValue Max value.
     * @return Integer value.
     */
    protected static int getRandomInt(int maxValue) {
        return (int) Math.rint(maxValue * Math.random());
    }

    /**
     * Returns a random integer, between a min and max value passed as parameters.
     * @param minValue Min value.
     * @param maxValue Max value.
     * @return Integer value.
     */
    protected static int getRandomInt(int minValue, int maxValue) {
        if (minValue > maxValue) {
            throw new IllegalArgumentException("El valor mínimo debe ser mayor que el máximo.");
        }
        return (int) Math.rint((maxValue - minValue) * Math.random()) + minValue;
    }
}
