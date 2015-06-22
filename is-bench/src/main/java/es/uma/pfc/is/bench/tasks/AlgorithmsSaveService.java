
package es.uma.pfc.is.bench.tasks;

import es.uma.pfc.is.bench.algorithms.AlgorithmsModel;
import es.uma.pfc.is.commons.persistence.PropertiesPersistence;
import java.util.Properties;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class AlgorithmsSaveService extends Service {
    public static final String ALG_PROPERTIES_PATH = System.getProperty("user.home") + "\\.isbench\\algorithms.properties";
    AlgorithmsModel model;
    

    public AlgorithmsSaveService(AlgorithmsModel model) {
        this.model = model;
    }


    
    @Override
    protected Task createTask() {
        return new Task() {

            
            @Override
            protected Object call() throws Exception {
                Properties values = PropertiesPersistence.load(ALG_PROPERTIES_PATH);
                System.out.println("Values: " + values);
                
                String prefix = model.getShortName() + ".";
                values.setProperty(prefix + "name", model.getName());
                values.setProperty(prefix + "short", model.getShortName());
                values.setProperty(prefix + "class", model.getClassName());
                PropertiesPersistence.save(ALG_PROPERTIES_PATH, values);
                return null;
            }
            
        };
    }

}
