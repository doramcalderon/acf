package es.uma.pfc.implications.generator;

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
        ImplicationalSystem sigma = new ImplicationalSystem();
        // addition of elements
        for (int i = 0; i < nodesNumber; i++) {
            sigma.addElement(i);
        }
        // addition of rules
        while (sigma.getRules().size() < rulesNumber) {
//            ComparableSet conclusion = new ComparableSet();
//            size = random.nextInt(maxLongConclusions);
//            
//            while(conclusion.size() < size) {
//                choice = (int) Math.rint(nodesNumber * Math.random());
//                int j = 1;
//                for (Comparable c : sigma.getSet()) {
//                    if (j == choice) {
//                        conclusion.add(c);
//                        break;
//                    }
//                    j++;
//                }
//            }
            ComparableSet conclusion = getConclusion(sigma, maxLongConclusions);
            ComparableSet premisse = getPremise(sigma, maxLongPremises);
//            ComparableSet premisse = new ComparableSet();
//            size = random.nextInt(maxLongPremises);
//            Iterator<Comparable> sigmaIterator = sigma.getSet().iterator();
//            Comparable c;
//            while (sigmaIterator.hasNext() && premisse.size() < size) {
//                c = sigmaIterator.next();
//                choice = (int) Math.rint(nodesNumber * Math.random());
//                if (choice < nodesNumber / 5) {
//                    premisse.add(c);
//                }
//            }
            sigma.addRule(new Rule(premisse, conclusion));
        }
        return sigma;
        
    }
    
    protected static ComparableSet getConclusion(ImplicationalSystem system, Integer maxLength) {
        ComparableSet conclusion = new ComparableSet();
        int nodesNumber = system.getSet().size();
        int size = (maxLength != null) ?  getRandomInt(maxLength) : getRandomInt(nodesNumber);
        int choice;
        System.out.println("Random conclusion with " + size + "nodes...");
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
    
    protected static ComparableSet getPremise(ImplicationalSystem system, Integer maxLength) {
        ComparableSet premisse = new ComparableSet();
        int nodesNumber = system.getSet().size();
        int size = (maxLength != null) ? getRandomInt(maxLength) : getRandomInt(nodesNumber);
        Iterator<Comparable> sigmaIterator = system.getSet().iterator();
        int choice;
        Comparable c;
        System.out.println("Random premisse with " + size + "nodes...");
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
