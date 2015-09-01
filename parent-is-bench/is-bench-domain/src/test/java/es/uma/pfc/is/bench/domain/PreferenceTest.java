/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.domain;

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
        Assert.assertEquals(p.getKey(), unmarshalPreference.getKey());
        Assert.assertEquals(p.getValue(), unmarshalPreference.getValue());
    }
    
}
