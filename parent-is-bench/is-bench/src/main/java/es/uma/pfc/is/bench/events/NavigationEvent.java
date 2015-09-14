
package es.uma.pfc.is.bench.events;

/**
 * It's published for navigate to a view.
 * @author Dora Calderón
 */
public class NavigationEvent {
    /**
     * View root..
     */
    private String view;

    /**
     * Constructor.
     * @param view View root.
     */
    public NavigationEvent(String view) {
        this.view = view;
    }

    /**
     * View root.
     * @return the view
     */
    public String getView() {
        return view;
    }

    /**
     * View root.
     * @param view the view to set
     */
    public void setView(String view) {
        this.view = view;
    }
    
    
}
