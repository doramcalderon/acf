
package es.uma.pfc.is.bench.tasks;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.algorithms.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.algorithms.domain.Algorithms;
import es.uma.pfc.is.bench.config.UserConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javax.xml.bind.JAXB;

/**
 * Load the algorithms declared in algorithms.properties file in the actual workspace.
 * @author Dora Calderón
 */
public class AlgorithmsLoadService extends Service<List<Algorithm>>{
    

    @Override
    protected Task<List<Algorithm>> createTask() {
        return new Task<List<Algorithm>>() {

            @Override
            protected List<Algorithm> call() throws Exception {
                List<Algorithm> algorithms = new ArrayList();
                
                File algorithmsXml = UserConfig.get().getAlgorithmsFile();
                if(algorithmsXml != null) {
                    Algorithms values = JAXB.unmarshal(algorithmsXml, Algorithms.class);
                    if(values != null) {
                        
                        for(AlgorithmEntity entity : values.getAlgorithms()) {
                            if(entity != null) {
                                algorithms.add(entity.getType().newInstance());
                            }
                        }
                    }

                }
                return algorithms;
            }
            
        };
    }

  
}
