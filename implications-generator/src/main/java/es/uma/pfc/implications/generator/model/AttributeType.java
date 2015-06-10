/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.implications.generator.model;

import java.util.Objects;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class AttributeType {
    public static AttributeType LETTER = new AttributeType(1, "a, b, c, ...");
    public static AttributeType NUMBER = new AttributeType(2, "1, 2, 3, ...");
    public static AttributeType INDEXED_LETTER = new AttributeType(3, "a1, a2, a3, ...");
    
    /** Identificador único.**/
    private Integer id;
    /** Nombre. **/
    private String label;
    /** Descripción. **/
    private String description;

    /**
     * Constructor.
     */
    public AttributeType() {
    }

    /**
     * Constructor.
     * @param id Identificador único.
     * @param name Nombre.
     * @throws IllegalArgumentException Si el identificador es nulo o pertenece a uno de los tipos por defecto<br/>
     * {@link #LETTER}, {@link #NUMBER},{@link #INDEXED_LETTER}.
     */
    public AttributeType(Integer id, String name) {
        if (id == null) {
            throw new IllegalArgumentException("El identificador no puede ser nulo.");
        }
        this.id = id;
        this.label = name;
    }
    /**
     * Identificador único.
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Identificador único.
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Nombre.
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Nombre.
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Descripción.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Descripción.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return label;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AttributeType other = (AttributeType) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
