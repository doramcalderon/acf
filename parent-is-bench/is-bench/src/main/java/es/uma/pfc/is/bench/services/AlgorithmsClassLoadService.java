
package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.AlgorithmInfo;
import es.uma.pfc.is.algorithms.optbasis.ClaAlgorithm;
import es.uma.pfc.is.algorithms.optbasis.DirectOptimalBasis;
import es.uma.pfc.is.commons.files.FileUtils;
import es.uma.pfc.is.commons.reflection.ReflectionUtil;
import es.uma.pfc.is.bench.config.WorkspaceManager;
import es.uma.pfc.is.bench.domain.ws.Preferences;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Service which loads the classes name of algorithms implementations found in the workspace classpath.
 */
public class AlgorithmsClassLoadService extends Service<List<AlgorithmInfo>> {
    private WorkspaceManager wsManager;
    /**
     * Constructor.
     */
    public AlgorithmsClassLoadService() {
        wsManager = WorkspaceManager.get();
    }

    /**
     * Searches the implementations of Algorithm interface and returns its names.
     *
     * @return List of classes names.
     */
    @Override
    protected Task<List<AlgorithmInfo>> createTask() {
        return new Task<List<AlgorithmInfo>>() {

            @Override
            protected List<AlgorithmInfo> call() throws Exception {
                
                List<AlgorithmInfo> algorithms = getAlgorithmsImpl(
                        Paths.get(wsManager.getPreference(Preferences.ALGORITHMS_PATH)));
                return algorithms;
            }

        };
    }

    /**
     * This method is executed when the background task is completed with errors.<br/>
     */
    @Override
    protected void failed() {
        Logger.getLogger(getClass().getName()).severe(getException().getMessage());
        getException().printStackTrace();
    }


    /**
     * Searches the algorithms implementations that contains the jars into current workspace lib folder.
     * @param libPath Directory that contains libraries.
     * @return List of algorithm classes.
     * @throws IOException 
     * @throws IllegalArgumentException When {@code libsPath} is null or empty.
     */
    protected List<AlgorithmInfo> getAlgorithmsImpl(Path libPath) throws IOException, URISyntaxException {
        if(libPath == null) {
            throw new IllegalArgumentException("The libsPath argument is mandatory");
        }
        List<AlgorithmInfo> algorithmsClasses = new ArrayList<>();
        algorithmsClasses.addAll(getAlgorithmInfos(Arrays.asList(DirectOptimalBasis.class, ClaAlgorithm.class), null));
                        
        final List<URL> urlLibs = FileUtils.getURLs(libPath, "*.jar");                
        Set<Class<? extends Algorithm>> classes = new HashSet<>();
        String libraryName = null;
        
        try(URLClassLoader classLoader = ReflectionUtil.getClassLoader(libPath)) {
            for(URL url : urlLibs) {
                try (JarFile jar = new JarFile(new File(url.toURI()))) {
                    libraryName = jar.getName();
                    classes.clear();
                    classes.addAll(ReflectionUtil.getImplFromJar(jar, classLoader, Algorithm.class));
                    algorithmsClasses.addAll(getAlgorithmInfos(classes, libraryName));   
                }
            }
            
        }
        
        return algorithmsClasses;
    }
    
    
    
    /**
     * Returns the names of the classes collection.
     * @param classes Classes.
     * @return List of classes name.
     */
    protected List<String> getClassesName(Collection<Class<?>> classes) {
        List<String> names = new ArrayList();

        if (classes != null) {
            classes.stream().forEach(clazz -> names.add(clazz.getName()));
        }
        return names;
    }
    
    protected List<AlgorithmInfo> getAlgorithmInfos(Collection<Class<? extends Algorithm>> classes, String library) {
        List<AlgorithmInfo> algorithms = null;
        
        if(classes != null) {
            algorithms = new ArrayList();
            AlgorithmInfo algorithm;
            
            for(Class<? extends Algorithm> clazz : classes) {
                algorithm = new AlgorithmInfo();
                algorithm.setType(clazz.getName());
                algorithm.setName(clazz.getName());
                algorithm.setShortName(clazz.getSimpleName());
                algorithm.setLibrary(library);
                algorithms.add(algorithm);
            }
        }
        return algorithms;
    }
}
