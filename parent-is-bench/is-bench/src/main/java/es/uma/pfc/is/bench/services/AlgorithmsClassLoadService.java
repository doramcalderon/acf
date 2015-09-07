
package es.uma.pfc.is.bench.services;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.commons.files.FileUtils;
import es.uma.pfc.is.commons.reflection.ReflectionUtil;
import es.uma.pfc.is.commons.workspace.WorkspaceManager;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

/**
 *
 * @since @author Dora Calderón
 */
public class AlgorithmsClassLoadService extends Service<List<String>> {

    /**
     * Constructor.
     */
    public AlgorithmsClassLoadService() {
    }

    /**
     * Searches the implementantios of Algorithm interface and returns its names.
     *
     * @return List of classes names.
     */
    @Override
    protected Task<List<String>> createTask() {
        return new Task<List<String>>() {

            @Override
            protected List<String> call() throws Exception {
                List<Class<?>> classesInClasspath = getAlgorithmsImplInClasspath();
                List<Class<?>> classes = getAlgorithmsImpl(Paths.get(WorkspaceManager.get().currentWorkspace().getLocation(), "lib"));
                classes.addAll(classesInClasspath);
                
                return getClassesName((Collection<Class<?>>) classes);
            }

        };
    }

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
    protected List<Class<?>> getAlgorithmsImplInClasspath() throws IOException {
        List<Class<?>> algorithmsClasses = null;
        Reflections r = new Reflections();
        Set<Class<? extends Algorithm>> classes = r.getSubTypesOf(Algorithm.class);
        
        if (classes != null) {
            algorithmsClasses = classes.stream().filter(clazz -> ReflectionUtil.isImplementation(clazz))
                                                .collect(Collectors.toList());   
        }
        return (algorithmsClasses != null) ? algorithmsClasses : new ArrayList<>();
    }

    /**
     * Searches the algorithms implementations that contains the jars into current workspace lib folder.
     * @param libPath Directory that contains libraries.
     * @return List of algorithm classes.
     * @throws IOException 
     * @throws IllegalArgumentException When {@code libsPath} is null or empty.
     */
    protected List<Class<?>> getAlgorithmsImpl(Path libPath) throws IOException {
        if(libPath == null) {
            throw new IllegalArgumentException("The libsPath argument is mandatory");
        }
        List<Class<?>> algorithmsClasses = null;
                        
        final List<URL> urlLibs = getURLs(libPath, "*.jar");                
        Reflections r = new ConfigurationBuilder().setUrls(urlLibs).build();
        Set<Class<? extends Algorithm>> classes = r.getSubTypesOf(Algorithm.class);
        
        if (classes != null) {
            algorithmsClasses = classes.stream().filter(clazz -> ReflectionUtil.isImplementation(clazz))
                                                .collect(Collectors.toList());   
        }
        return (algorithmsClasses != null) ? algorithmsClasses : new ArrayList<>();
    }
    
    /**
     * Return the URLS of files of libPath, that matches the {@code filter}.
     * @param libPath Path.
     * @param filter Filter.
     * @return List of URL's.
     * @throws IOException 
     */
    protected List<URL> getURLs(Path libPath, String filter) throws IOException {
        FileUtils.createDirIfNoExists(libPath.toString());
        final List<URL> urlLibs = new ArrayList();
        try(DirectoryStream<Path> ds = Files.newDirectoryStream(libPath, filter)) {
            ds.forEach(path -> {
                try {
                    urlLibs.add(path.toUri().toURL());
                } catch (MalformedURLException ex) {
                    Logger.getLogger(AlgorithmsClassLoadService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        return urlLibs;
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
}
