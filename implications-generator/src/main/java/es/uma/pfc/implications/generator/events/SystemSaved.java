
package es.uma.pfc.implications.generator.events;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class SystemSaved {
    /**
     * Path of system saved.
     */
    private String path;

    /**
     * Constructor.
     * @param path Path of system saved.
     */
    public SystemSaved(String path) {
        this.path = path;
    }

    /**
     * Path of system saved.
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Path of system saved.
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }
    
    
}
