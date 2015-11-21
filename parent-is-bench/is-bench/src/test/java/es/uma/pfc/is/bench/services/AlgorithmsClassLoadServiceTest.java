
package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.optbasis.ClaAlgorithm;
import es.uma.pfc.is.algorithms.optbasis.DirectOptimalBasis;
import es.uma.pfc.is.commons.files.FileUtils;
import es.uma.pfc.is.commons.reflection.ReflectionUtil;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Dora Calderón
 */
public class AlgorithmsClassLoadServiceTest {
    
    public AlgorithmsClassLoadServiceTest() {
    }

    /**
     * Test of createTask method, of class AlgorithmsClassLoadService.
     */
    @Test
    public void testCreateTask() {
    }

    /**
     * Test of failed method, of class AlgorithmsClassLoadService.
     */
    @Test
    public void testFailed() {
    }

    
    public void testGetURLs() throws MalformedURLException, IOException {
        Path libPath = Paths.get(System.getProperty("localRepository"), "es", "uma", "pfc", "is-algorithms", "1.0.0-SNAPSHOT");
        String filter = "*.jar";
        List<URL> expectedList = Arrays.asList(Paths.get(libPath.toString(), "is-algorithms-1.0.0-SNAPSHOT.jar").toUri().toURL());
        
        AlgorithmsClassLoadService service = new AlgorithmsClassLoadService();
        List<URL> urlsList = FileUtils.getURLs(libPath, filter);
        
        assertNotNull(urlsList);
        assertEquals(expectedList.size(), urlsList.size());
        expectedList.forEach(u -> assertTrue(urlsList.contains(u)));
    }
  
    /**
     * Test of getClassesName method, of class AlgorithmsClassLoadService.
     */
    @Test
    public void testGetClassesName() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(String.class);
        classes.add(Integer.class);
        List<String> classesNameExpected = Arrays.asList(String.class.getName(), Integer.class.getName());
        
        AlgorithmsClassLoadService service = new AlgorithmsClassLoadService();
        List<String> classesName = service.getClassesName(classes);
        
        assertNotNull(classesName);
        assertEquals(classesNameExpected.size(), classesName.size());
        classesName.forEach(className -> assertTrue(classesNameExpected.contains(className)));
        
    }
    @Test
    public void testGetClassesName_Null() {
        Set<Class<?>> classes = null;
        List<String> classesNameExpected = new ArrayList();
        
        AlgorithmsClassLoadService service = new AlgorithmsClassLoadService();
        List<String> classesName = service.getClassesName(classes);
        
        assertNotNull(classesName);
        assertEquals(classesNameExpected.size(), classesName.size());
        
    }

    
    /**
     * Test of getAlgorithmsImpl method, of class AlgorithmsClassLoadService.
     */
    @Test
    public void testGetAlgorithms_Empty() throws Exception {
        Path libPath = Paths.get(System.getProperty("user.dir"), "target", "lib");
        AlgorithmsClassLoadService service = new AlgorithmsClassLoadService();
        
        List<Class<? extends Algorithm>> algorithms = service.getAlgorithmsImpl(libPath);
        
        assertNotNull(algorithms);
        assertFalse(algorithms.isEmpty());
        assertEquals(2, algorithms.size());
        assertTrue(algorithms.contains(DirectOptimalBasis.class));
        assertTrue(algorithms.contains(ClaAlgorithm.class));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetAlgorithms_PathNull() throws Exception {
        Path libPath = null;
        AlgorithmsClassLoadService service = new AlgorithmsClassLoadService();
        service.getAlgorithmsImpl(libPath);
    }
    
    @Test
    public void testGetAlgorithms() throws Exception {
        Path libPath = Paths.get(System.getProperty("localRepository"), "es", "uma", "pfc", "is-algorithms", "1.0.0-SNAPSHOT");
        AlgorithmsClassLoadService service = new AlgorithmsClassLoadService();
        
        List<Class<? extends Algorithm>> algClasses = service.getAlgorithmsImpl(libPath);
        
        assertNotNull(algClasses);
        assertFalse(algClasses.isEmpty());
        algClasses.forEach(clazz -> assertTrue(ReflectionUtil.isImplementation(clazz)));
    }
}
