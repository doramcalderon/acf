/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.benchmarks.domain;

import es.uma.pfc.is.algorithms.optbasis.DirectOptimalBasis;
import es.uma.pfc.is.bench.algorithms.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.algorithms.domain.Algorithms;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

/**
 *
 * @author Dora Calderón
 */
public class BenchmarksTest {
    private static final String path = System.getProperty("user.dir") + "/src/test/resources/";
    
    public BenchmarksTest() {
    }
    
    @AfterClass
    public  static void tearDown() throws IOException {
        Path filePath = Paths.get(path, "benchmarks.xml");
        Files.deleteIfExists(filePath);
    }

    /**
     * Test of getBenchmarks method, of class Benchmarks.
     */
    @Test
    public void testGetBenchmarks() {
    }
 

     @Test
    public void toXML() throws JAXBException {
        Algorithms algorithms = new Algorithms();
        algorithms.add(new AlgorithmEntity(new DirectOptimalBasis()));
        
        BenchmarkEntity benchmark = new BenchmarkEntity("BENCH 1");
        benchmark.setInput("C:\\.isbench\\myws\\bench1\\implications.txt");
        benchmark.setAlgorithms(algorithms);
        
        List<BenchmarkEntity> benchmarksSet = new ArrayList<>();
        benchmarksSet.add(benchmark);
        
        Benchmarks benchs = new Benchmarks();
        benchs.setBenchmarks(benchmarksSet);
        File xmlFile = new File(path + "benchmarks.xml");

        JAXBContext context = JAXBContext.newInstance(Benchmarks.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(benchs, xmlFile);
        
        Benchmarks unmarshalBenchmarks = (Benchmarks) context.createUnmarshaller().unmarshal(xmlFile);

        assertArrayEquals(benchs.getBenchmarks().toArray(new BenchmarkEntity[]{}), 
                          unmarshalBenchmarks.getBenchmarks().toArray(new BenchmarkEntity[]{}));
    }

}
