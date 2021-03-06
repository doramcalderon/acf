
package es.uma.pfc.is.bench.domain;

import es.uma.pfc.is.algorithms.AlgorithmInfo;
import es.uma.pfc.is.algorithms.AlgorithmResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import javax.xml.bind.JAXB;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class BenchmarkResultSetTest {
    
    
    @Test
    public void testPersist() throws IOException {
        AlgorithmInfo algInfo = new AlgorithmInfo();
        algInfo.setName("Direct Optimal Basis");
        
        AlgorithmResult ar1 = new AlgorithmResult("input.txt", "output.txt", algInfo);
        AlgorithmResult ar2 = new AlgorithmResult("input.txt", "output.txt", algInfo);
        
        BenchmarkResult r1 = new BenchmarkResult();
        r1.setBenchmarkName("bench1");
        r1.setAlgorithmResults(Arrays.asList(ar1, ar2));
        
        
        BenchmarkResult r2 = new BenchmarkResult();
        r2.setBenchmarkName("bench1");
        
        
        BenchmarkResultSet resultSet = new BenchmarkResultSet();
        resultSet.setName("bench1");
        resultSet.getResults().add(r1);
        resultSet.getResults().add(r2);
        
        StringWriter writer = new StringWriter();
        JAXB.marshal(resultSet, writer);
        System.out.println(writer.toString());
        BenchmarkResultSet unmarshalResultSet = JAXB.unmarshal(new StringReader(writer.toString()), BenchmarkResultSet.class);
        writer.close();
        
        assertEquals(resultSet.getResults().size(), unmarshalResultSet.getResults().size());
        assertTrue(unmarshalResultSet.getResults().containsAll(resultSet.getResults()));
    }
    
}
