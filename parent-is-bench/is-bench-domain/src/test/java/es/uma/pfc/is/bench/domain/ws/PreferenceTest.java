
package es.uma.pfc.is.bench.domain.ws;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXB;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Dora Calderón
 */
public class PreferenceTest {
    
    public PreferenceTest() {
    }

    @Test
    public void testMarshalUnmarshal() {
        Preference p = new Preference("language", "ES");
        StringWriter sw = new StringWriter();
        
        JAXB.marshal(p, sw);
        Preference unmarshalPreference = JAXB.<Preference>unmarshal(new StringReader(sw.toString()), Preference.class);
        
        Assert.assertNotNull(unmarshalPreference);
        Assert.assertEquals(p.getName(), unmarshalPreference.getName());
        Assert.assertEquals(p.getValue(), unmarshalPreference.getValue());
    }
    
}
