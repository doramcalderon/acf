

package es.uma.pfc.is.commons.io;

import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import fr.kbertet.lattice.io.ImplicationalSystemWriter;
import fr.kbertet.lattice.io.ImplicationalSystemWriterFactory;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.SortedSet;

/**
 * Writes an ImplicationalSystem in a Prolog file.
 * @author Dora Calderón
 */
public class ImplicationalSystemWriterProlog implements ImplicationalSystemWriter {
    /**
     * String para una nueva línea.
     */
    private static final String NEW_LINE = System.lineSeparator();

    /**
     * Constructor.
     */
    private ImplicationalSystemWriterProlog() {
    }
    /**
     * Instancia única.
     */
    private static ImplicationalSystemWriterProlog instance;

    /**
     * Devuelve una instancia única de ImplicationalSystemWriterProlog.
     *
     * @return  Instancia única.
     */
    public static ImplicationalSystemWriterProlog getInstance() {
        if (instance == null) {
            instance = new ImplicationalSystemWriterProlog();
        }
        return instance;
    }

      /**
     * Register this class for writing .pl files.
     */
    public static void register() {
        ImplicationalSystemWriterFactory.register(ImplicationalSystemWriterProlog.getInstance(), "pl");
    }

      /**
     * Unregister this class for writing .pl files.
     */
    public static void unregister() {
        ImplicationalSystemWriterFactory.unregister("pl");
    }
    
    /**
     * Guarda en un fichero un sistema en formato Prolog.
     * @param system Systema de implicaciones.
     * @param file Fichero destino.
     * @throws IOException Cuando hay un error al acceder al fichero.
     */
    @Override
    public void write(ImplicationalSystem system, BufferedWriter file) throws IOException {
        file.write(getSystemInPrologFormat(system));
    }

    /**
     * Devuelve el sistema de implicaciones parseado en formato Prolog.
     * @param system Sistema de implicaciones.
     * @return Sistema parseado.
     */
    public String getSystemInPrologFormat(ImplicationalSystem system) {
        StringBuilder sb = new StringBuilder();
        sb.append(":-dynamic(implication/2),dynamic(attributes/1),dynamic(dfimplied/1).")
                .append(NEW_LINE).append(NEW_LINE)
                .append(parseNodes(system.getSet())).append(NEW_LINE)
                .append(parseImplications(system.getRules()));
        
        return sb.toString();
    }
    /**
     * Parsea la declaración de los nodos del sistema de implicaciones.
     * @param nodes Nodos.
     * @return Declaración de nodos en el formato: {@code attribute([a,b,c,d,e])}.
     */
    public String parseNodes(SortedSet<Comparable> nodes) {
        return "attributes(" + parseAttributes(nodes) + ").";
    }
    /**
     * Parsea las implicaciones del sistema.
     * @param implications Implicaciones.
     * @return Implicaciones en el formato: {@code implication([a,b],[c]).}.
     */
    public String parseImplications(SortedSet<Rule> implications) {
        StringBuilder sb = new StringBuilder();
        
        if(implications != null && !implications.isEmpty()) {
            for(Rule implication : implications) {
                sb.append("implication(");
                sb.append(parseAttributes(implication.getPremise()))
                  .append(",")
                  .append(parseAttributes(implication.getConclusion()));
                sb.append(").").append(NEW_LINE);
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Parsea un conjunto de atributos.
     * @param attributes Atributos.
     * @return String que representa un conjunto de atributos en el formato: {@code [a,b,c,d]}.
     */
    public String parseAttributes(SortedSet<Comparable> attributes) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("[");
        if(attributes != null && !attributes.isEmpty()) {
            for(Comparable attribute : attributes) {
                sb.append(attribute).append(",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));    
        }
        sb.append("]");
        return sb.toString();
    }
    
    
}
