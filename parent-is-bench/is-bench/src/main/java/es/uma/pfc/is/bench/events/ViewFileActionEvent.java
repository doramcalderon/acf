
package es.uma.pfc.is.bench.events;

import java.io.File;
import java.util.ResourceBundle;
import javafx.stage.Window;

/**
 * Is published for view a file in the viewer.
 * @author Dora Calderón
 */
public class ViewFileActionEvent {
    /**
     * File to view.
     */
    private final File file;
    /**
     * Title of viewer.
     */
    private String title;
    /**
     * Parent window.
     */
    private Window parent;
    /**
     * I18n Resources.
     */
    private ResourceBundle i18nResources;
    /**
     * Constructor.
     * @param file File.
     * @param title Title of viewer.
     * @param Parent window.
     */
    public ViewFileActionEvent(File file, String title, Window parent, ResourceBundle i18nResources) {
        this.file = file;
        this.title = title;
        this.parent = parent;
        this.i18nResources = i18nResources;
    }

    /**
     * File to view.
     * @return File.
     */
    public File getFile() {
        return file;
    }

    /**
     * Title of viewer.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Parent window.
     * @return the parent
     */
    public Window getParent() {
        return parent;
    }

    /**
     * I18n Resources.
     * @return the i18nResources
     */
    public ResourceBundle getI18nResources() {
        return i18nResources;
    }
    
    

}
