
package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.AlgorithmInfo;
import es.uma.pfc.is.algorithms.optbasis.ClaAlgorithm;
import es.uma.pfc.is.algorithms.optbasis.DirectOptimalBasis;
import es.uma.pfc.is.bench.business.AlgorithmsBean;
import es.uma.pfc.is.commons.files.FileUtils;
import es.uma.pfc.is.commons.reflection.ReflectionUtil;
import es.uma.pfc.is.bench.config.WorkspaceManager;
import es.uma.pfc.is.bench.domain.ws.Preferences;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.reflections.Reflections;

/**
 * Service which loads the classes name of algorithms implementations found in the workspace classpath.
 */
public class AlgorithmsClassLoadService extends Service<List<AlgorithmInfo>> {

    /**
     * Constructor.
     */
    public AlgorithmsClassLoadService() {
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
                
                List<Class<? extends Algorithm>> classes = getAlgorithmsImpl(
                        Paths.get(WorkspaceManager.get().getPreference(Preferences.ALGORITHMS_PATH)));
                
                
                return getAlgorithmInfos(classes);
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
    protected List<Class<? extends Algorithm>> getAlgorithmsImpl(Path libPath) throws IOException, URISyntaxException {
        if(libPath == null) {
            throw new IllegalArgumentException("The libsPath argument is mandatory");
        }
        List<Class<? extends Algorithm>> algorithmsClasses = new ArrayList<>();
        algorithmsClasses.add(DirectOptimalBasis.class);
        algorithmsClasses.add(ClaAlgorithm.class);
                        
        final List<URL> urlLibs = FileUtils.getURLs(libPath, "*.jar");                
        
        ClassLoader classLoader = ReflectionUtil.getClassLoader(libPath);
        Set<Class<? extends Algorithm>> classes = new HashSet<>();
        for(URL url : urlLibs) {
            try (JarFile jar = new JarFile(new File(url.toURI()))) {
                classes.addAll(ReflectionUtil.getClassesFromJar(jar, classLoader, Algorithm.class));
            }
        }
        
        
        algorithmsClasses.addAll(classes.stream().filter(clazz -> ReflectionUtil.isImplementation(clazz))
                                                .collect(Collectors.toList()));   
        
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
    
    protected List<AlgorithmInfo> getAlgorithmInfos(Collection<Class<? extends Algorithm>> classes) {
        List<AlgorithmInfo> algorithms = null;
        
        if(classes != null) {
            algorithms = new ArrayList();
            AlgorithmInfo algorithm;
            
            for(Class<? extends Algorithm> clazz : classes) {
                algorithm = new AlgorithmInfo();
                algorithm.setType(clazz.getName());
                algorithm.setName(clazz.getName());
                algorithm.setShortName(clazz.getSimpleName());
                algorithms.add(algorithm);
            }
        }
        return algorithms;
    }
}
