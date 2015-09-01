/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



/**
 *
 * @since 
 * @author Dora Calderón
 */
public class Preference {
    @XmlAttribute
    private String key;
    @XmlAttribute
    private String value;
    
    public Preference() {
    }

    
    public Preference(String key, String value) {
        this.key = key;
        this.value = value;
    }

    
    
    public String getKey() {
        return key;
    }

    
    public String getValue() {
        return value;
    }


}
