package es.uma.pfc.implications.generator;


import es.uma.pfc.implications.generator.model.ImplicationsModel;
import es.uma.pfc.is.commons.io.ImplicationalSystemWriterProlog;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import fr.kbertet.util.ComparableSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Factoría de sistemas implicacionales.
 * @author Dora Calderón
 */
public class ImplicationsFactory {
    /**
     * Inicialización.
     */
    public static void initialize() {
        ImplicationalSystemWriterProlog.register();
    }
    /**
     * Genera un sistema aleatorio con el número de nodos e implicaciones pasados como parámetro.
     * @param nodesNumber Número de nodos.
     * @param rulesNumber Número de implicaciones.
     * @return Sistema implicacional.
     */
    public static ImplicationalSystem getImplicationalSystem(int nodesNumber, int rulesNumber) {
        return ImplicationalSystem.random(nodesNumber, rulesNumber);
    }


    /**
     * Devuelve n sistemas aleatorios de implicaciones cumpliendo las restricciones establecidas en el parámetro
     * {@code implicationsModel}.
     *
     * @param implicationsModel Restricciones para los sistemas de implicaciones a generar.
     * @return Lista de sistemas de implicaciones aleatorios.
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
     * Devuelve un sistema aleatorio de implicaciones cumpliendo las restricciones establecidas en el parámetro
     * {@code implicationsModel}.
     *
     * @param implicationsModel Restricciones para el sistema de implicaciones a generar.
     * @return Conjunto de implicaciones aleatorio.
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
     * Devuelve una conclusion con un número máximo de nodos. Si {@code maxLength} es nulo o menor o igual que 0, 
     * el número de nodos máximo es el número de nodos del sistema.
     *
     * @param system Sistema.
     * @param minLength Número mínimo de nodos.
     * @param maxLength Número máximo de nodos.
     * @return Conclusiones.
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
     * Devuelve una premisa con un número máximo de nodos.<br/>
     * Si {@code maxLength} es nulo 0 menor que 0, el número de nodos máximo es el número de nodos del sistema.
     *
     * @param system Sistema.
     * @param minLength Número mínimo de nodos.
     * @param maxLength Número máximo de nodos.
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
     * Devuelve un entero aleatorio, entre 0 y el valor pasado como parámetro.
     *
     * @param maxValue Valor máximo.
     * @return Valor entero.
     */
    protected static int getRandomInt(int maxValue) {
        return (int) Math.rint(maxValue * Math.random());
    }

    /**
     * Devuelve un entero aleatorio, entre un valor mínimo y un valor máximo pasados como parámetro.
     *
     * @param minValue Valor mínimo.
     * @param maxValue Valor máximo.
     * @return Valor entero.
     */
    protected static int getRandomInt(int minValue, int maxValue) {
        if (minValue > maxValue) {
            throw new IllegalArgumentException("El valor mínimo debe ser mayor que el máximo.");
        }
        return (int) Math.rint((maxValue - minValue) * Math.random()) + minValue;
    }
}
