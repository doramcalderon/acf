/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.implications.generator.view;

import es.uma.pfc.implications.generator.ImplicationsFactory;
import es.uma.pfc.implications.generator.model.ImplicationsModel;
import fr.kbertet.lattice.ImplicationalSystem;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @since @author Dora Calderón
 */
public class GenerateService extends Service<List<ImplicationalSystem>> {
    /** Modelo con las condiciones para generar el conjunto.**/
    private ImplicationsModel model;

    /**
     * Constructor.
     */
    public GenerateService() {
    }

    /**
     * Constructor.
     * @param model  Modelo con las condiciones para generar el conjunto.
     */
    public GenerateService(ImplicationsModel model) {
        this.model = model;
    }
    
    

    @Override
    protected Task<List<ImplicationalSystem>> createTask() {
        return new Task<List<ImplicationalSystem>>() {
            @Override
            protected List<ImplicationalSystem> call() {
                updateMessage("Generando conjuntos aleatorios ...");
                List<ImplicationalSystem> implicationSystems = ImplicationsFactory.getImplicationalSystems(getModel());
                return implicationSystems;
            }

        };
    }

    /**
     * Modelo con las condiciones para generar el conjunto.
     * @return the model
     */
    public ImplicationsModel getModel() {
        return model;
    }

    /**
     * Modelo con las condiciones para generar el conjunto.
     * @param model the model to set
     */
    public void setModel(ImplicationsModel model) {
        this.model = model;
    }

}
