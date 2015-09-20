
package es.uma.pfc.implications.generator.events;

import java.util.Arrays;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class SystemSaved {
    /**
     * Path of system saved.
     */
    private String [] paths;

    /**
     * Constructor.
     * @param paths Paths of systems saved.
     */
    public SystemSaved(String ... paths) {
        if(paths != null) {
            this.paths = Arrays.copyOf(paths, paths.length);
        }
    }

    /**
     * Path of system saved.
     * @return the path
     */
    public String[] getPaths() {
        return paths;
    }

    /**
     * Paths of systems saved.
     * @param path the path to set
     */
    public void setPath(String ... paths) {
        this.paths = paths;
    }
    
    
}
