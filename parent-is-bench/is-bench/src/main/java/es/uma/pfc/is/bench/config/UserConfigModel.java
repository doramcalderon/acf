

package es.uma.pfc.is.bench.config;

import es.uma.pfc.is.bench.domain.ws.Workspace;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class UserConfigModel {
    private final ListProperty<Workspace> workspacesList;
    /**
     * Workspace selected.
     */
    private ObjectPropertyBase<Workspace>  workspaceSelected;
    /**
     * Preferences of selected workspace.
     */
    private final ListProperty<PreferenceModel> preferences;

    /**
     * Constructor.
     */
    public UserConfigModel() {
        workspacesList = new SimpleListProperty<>(FXCollections.observableArrayList());
        preferences = new SimpleListProperty<>(FXCollections.observableArrayList());
        workspaceSelected = new SimpleObjectProperty<>();
        workspaceSelected.addListener((ObservableValue<? extends Workspace> observable, Workspace oldValue, Workspace newValue) -> {
            updatePreferences();
        });
    }
    
    
    
    /**
     * Workspaces list property.
     * @return Worspaces list.
     */
    public ListProperty<Workspace> workspacesListProperty() {
        return workspacesList;
    }
    
    /**
     * Sets the workspaces list.
     * @param workspaces Workspaces paths.
     */
    public void setWorkspacesList(List<Workspace> workspaces) {
        workspacesList.setAll(workspaces.toArray(new Workspace[]{}));
    }

    /**
     * Workspace selected property.
     * @return Workspace selected.
     */
    public Property<Workspace> workspaceSelected() {
        return workspaceSelected;
    }
    /**
     * Workspace selected.
     * @return the workspaceSelected
     */
    public Workspace getWorkspaceSelected() {
        return workspaceSelected.get();
    }

    public ListProperty<PreferenceModel> preferencesProperty() {
        return preferences;
    }
    /**
     * Workspace selected.
     * @param workspaceSelected the workspaceSelected to set
     */
    public void setWorkspaceSelected(Workspace workspaceSelected) {
        this.workspaceSelected.set(workspaceSelected);
    }

    /**
     * Updates the preferences with the selected workspace's preferences.
     */
    protected final void updatePreferences() {
        Workspace wsSelected = workspaceSelected.get();
        preferences.get().clear();
        
        if(wsSelected != null && wsSelected.getPreferences() != null) {
            wsSelected.getPreferences().getPreferencesList().forEach(p -> preferences.get().add(new PreferenceModel(p.getName(), p.getValue())));
        }
        
    }
    
    
    
}
