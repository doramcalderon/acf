package es.uma.pfc.is.commons.io;

import es.uma.pfc.is.commons.strings.StringUtils;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import fr.kbertet.lattice.io.ImplicationalSystemReader;
import fr.kbertet.lattice.io.ImplicationalSystemReaderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 *
 * @since @author Dora Calderón
 */
public class ImplicationalSystemReaderProlog implements ImplicationalSystemReader {
    /**
     * Single instance.
     */
    private static ImplicationalSystemReaderProlog instance;
    /**
     * Constructor.
     */
    protected ImplicationalSystemReaderProlog() {
    }
    
      /**
     * Returns a ImplicationalSystemReaderProlog instance.
     * @return  Single instance.
     */
    public static ImplicationalSystemReaderProlog getInstance() {
        if (instance == null) {
            instance = new ImplicationalSystemReaderProlog();
        }
        return instance;
    }
    
    
     /**
     * Register this class for writing .pl files.
     */
    public static void register() {
        ImplicationalSystemReaderFactory.register(getInstance(), "pl");
    }

      /**
     * Unregister this class for writing .pl files.
     */
    public static void unregister() {
        ImplicationalSystemReaderFactory.unregister("pl");
    }

    @Override
    public void read(ImplicationalSystem system, BufferedReader file) throws IOException {
        if (file != null) {

            try {
                // header
                file.readLine();
                parseAttributes(system, file);
                parseImplications(system, file);
            } finally {
                file.close();
            }
        }

    }

    /**
     * Parses the implications from a file and saves in the system.
     *
     * @param system Implicational system.
     * @param file File.
     * @throws IOException
     */
    protected void parseImplications(ImplicationalSystem system, BufferedReader file) throws IOException {
        String line = file.readLine();
        while (!StringUtils.isEmpty(line)) {
            if (line.startsWith("implication")) {
                parseImplication(system, line);
            }
            line = file.readLine();
        }
    }

    /**
     * Parses an implication line.
     *
     * @param system Implicational system.
     * @param line Line.
     */
    protected void parseImplication(ImplicationalSystem system, String line) {
        String implication = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
        
        int endPremise =  implication.indexOf("]") + 1;
        String premise = implication.substring(implication.indexOf("["), endPremise);
        String conclusion = implication.substring(endPremise, implication.lastIndexOf("]") + 1);
        
        TreeSet<Comparable> premiseElements = parseImplicationPart(premise);
        TreeSet<Comparable> conclusionElements = parseImplicationPart(conclusion);
        
        system.addAllElements(premiseElements);
        system.addAllElements(conclusionElements);
        system.addRule(new Rule(premiseElements, conclusionElements));
    }

    /**
     * Parses an implication part (premise or conclusion) and adds to a TreeSet.
     *
     * @param part Implication part.
     * @return TreeSet.
     */
    protected TreeSet<Comparable> parseImplicationPart(String part) {
        TreeSet<Comparable> elements = new TreeSet();
        StringTokenizer st = new StringTokenizer(part.substring(part.indexOf("[") + 1, part.indexOf("]")), ",");
        while (st.hasMoreTokens()) {
            elements.add(st.nextToken());
        }
        return elements;
    }

    /**
     * Parses the attributes from a file and saves it in the system.
     *
     * @param system Implicational system.
     * @param file File.
     * @throws IOException
     */
    protected void parseAttributes(ImplicationalSystem system, BufferedReader file) throws IOException {
        String line = file.readLine();
        while (line != null && !line.startsWith("attributes")) {
            line = file.readLine();
        }
        parseAttributes(system, line);
    }

    /**
     * Parses the attributes line and adds to the system.
     *
     * @param system Implicational system.
     * @param line Attributes line.
     */
    protected void parseAttributes(ImplicationalSystem system, String line) {
        if (line != null) {
            String attributes = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
            StringTokenizer st = new StringTokenizer(attributes, ",");
            while (st.hasMoreTokens()) {
                system.addElement(st.nextToken());
            }
        }
    }

}
