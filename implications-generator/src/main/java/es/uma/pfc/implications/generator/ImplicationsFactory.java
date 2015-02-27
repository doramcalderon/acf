package es.uma.pfc.implications.generator;

import es.uma.pfc.implications.generator.model.NodeType;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import fr.kbertet.util.ComparableSet;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class ImplicationsFactory {
    private static final Random random = new Random();

    public static ImplicationalSystem getImplicationalSystem(int nodesNumber, int rulesNumber) {
        return ImplicationalSystem.random(nodesNumber, rulesNumber);
    }
    
    /**
     * Devuelve un conjunto aleatorio de implicaciones acotado por el número máximo de premisas y el número 
     * máximo de conclusiones.
     * Se modifica el algoritmo del método {@link ImplicationalSystem#random(int, int) } para que las premisas
     * y las conclusiones tengan como máximo los valores pasados como parámetro. Para ello se modifica la forma 
     * de obtener el número aleatorio, utilizando el método {@link Random#nextInt(int) }.
     * @param nodesNumber Número de nodos.
     * @param rulesNumber Número de reglas o implicaciones.
     * @param maxLongPremises Número máximo de elementos en la premisa.
     * @param maxLongConclusions Número máximo de elementos en las conclusiones.
     * @return 
     */
    public static ImplicationalSystem getImplicationalSystem(Integer nodesNumber, Integer rulesNumber, 
                                                             Integer maxLongPremises, Integer maxLongConclusions) {
        return getImplicationalSystem(nodesNumber, rulesNumber, maxLongPremises, maxLongConclusions, null);
        
    }
    
    /**
     * Devuelve un conjunto aleatorio de implicaciones acotado por el número máximo de premisas y el número 
     * máximo de conclusiones.
     * Se modifica el algoritmo del método {@link ImplicationalSystem#random(int, int) } para que las premisas
     * y las conclusiones tengan como máximo los valores pasados como parámetro. Para ello se modifica la forma 
     * de obtener el número aleatorio, utilizando el método {@link Random#nextInt(int) }.
     * @param nodesNumber Número de nodos.
     * @param rulesNumber Número de reglas o implicaciones.
     * @param maxLongPremises Número máximo de elementos en la premisa.
     * @param maxLongConclusions Número máximo de elementos en las conclusiones.
     * @param nodeType Tipo de los nodos.
     * @return Conjunto de implicaciones aleatorio.
     */
    public static ImplicationalSystem getImplicationalSystem(Integer nodesNumber, Integer rulesNumber, 
                                                             Integer maxLongPremises, Integer maxLongConclusions,
                                                             NodeType nodeType) {
        ImplicationalSystem sigma = new ImplicationalSystem();
        String [] nodes = NodesFactory.get().getNodes(nodeType, nodesNumber);
        // addition of elements
        for (int i = 0; i < nodesNumber; i++) {
            sigma.addElement(nodes[i]);
        }
        // addition of rules
        while (sigma.getRules().size() < rulesNumber) {
            ComparableSet conclusion = getConclusion(sigma, maxLongConclusions);
            ComparableSet premisse = getPremise(sigma, maxLongPremises);
            sigma.addRule(new Rule(premisse, conclusion));
        }
        return sigma;
        
    }
    
    /**
     * Devuelve una conclusion con un número máximo de nodos.
     * Si {@code maxLength} es nulo 0 menor que 0, el número de nodos máximo es el número de nodos del sistema.
     * @param system  Sistema.
     * @param maxLength Número máximo de nodos.
     * @return Conclusiones.
     */
    protected static ComparableSet getConclusion(ImplicationalSystem system, Integer maxLength) {
        ComparableSet conclusion = new ComparableSet();
        int nodesNumber = system.getSet().size();
        int size = (maxLength != null && maxLength > 0) ?  getRandomInt(maxLength) : getRandomInt(nodesNumber);
        int choice;
        
        while (conclusion.size() < size) {
            choice = getRandomInt(nodesNumber);
            int j = 1;
            for (Comparable c : system.getSet()) {
                if (j == choice) {
                    conclusion.add(c);
                    break;
                }
                j++;
            }
        }
        return conclusion;
    }
    
    /**
     * Devuelve una premisa con un número máximo de nodos.<br/>
     * Si {@code maxLength} es nulo 0 menor que 0, el número de nodos máximo es el número de nodos del sistema.
     * @param system  Sistema.
     * @param maxLength Número máximo de nodos.
     * @return Premisas.
     */
    protected static ComparableSet getPremise(ImplicationalSystem system, Integer maxLength) {
        ComparableSet premisse = new ComparableSet();
        int nodesNumber = system.getSet().size();
        int size = (maxLength != null && maxLength > 0) ? getRandomInt(maxLength) : getRandomInt(nodesNumber);
        Iterator<Comparable> sigmaIterator = system.getSet().iterator();
        int choice;
        Comparable c;
        
        while (sigmaIterator.hasNext() && premisse.size() < size) {
            c = sigmaIterator.next();
            choice = getRandomInt(nodesNumber);
            if (choice < nodesNumber / 5) {
                premisse.add(c);
            }
        }
        return premisse;
    }
    
    /**
     * Devuelve un entero aleatorio, entre 0 y el valor pasado como parámetro.
     * @param maxValue Válor máximo.
     * @return Valor entero.
     */
    protected static int getRandomInt(int maxValue) {
        return (int) Math.rint(maxValue * Math.random());
    }
}
