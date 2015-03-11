/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.implications.generator.model;




/**
 *
 * @since 
 * @author Dora Calderón
 */
public class ImplicationsModel {
    /**
     * Número de nodos.
     */
    private Integer nodes;
    /**
     * Número de implicaciones.
     */
    private Integer implications;
    /**
     * Número máximo de atributos en la premisa.
     */
    private Integer maxPremiseLength;
    /**
     * Número mínimo de atributos en la premisa.
     */
    private Integer minPremiseLength;
    /**
     * Número máximo de atributos en la conclusión.
     */
    private Integer maxConclusionLength;
    /**
     * Número mínimo de atributos en la conclusión.
     */
    private Integer minConclusionLength;
    /**
     * Tipo de los nodos.
     */
    private NodeType nodeType;
    /**
     * Si se dibuja el grafo.
     */
    private Boolean showImage;

    /**
     * Constructor.
     */
    public ImplicationsModel() {
        this.showImage = Boolean.FALSE;
    }
    
    
    /**
     * Constructor.
     * @param nodes Número de nodos.
     * @param implications Número de implicaciones.
     */
    public ImplicationsModel(Integer nodes, Integer implications) {
        this();
        this.nodes = nodes;
        this.implications = implications;
    }

    /**
     * Número de nodos.
     * @return the nodes
     */
    public Integer getNodes() {
        return nodes;
    }

    /**
     * Número de nodos.
     * @param nodes the nodes to set
     */
    public void setNodes(Integer nodes) {
        this.nodes = nodes;
    }

    /**
     * Número de implicaciones.
     * @return the implications
     */
    public Integer getImplications() {
        return implications;
    }

    /**
     * Número de implicaciones.
     * @param implications the implications to set
     */
    public void setImplications(Integer implications) {
        this.implications = implications;
    }

    /**
     * Número máximo de atributos en la premisa.
     * @return the maxPremiseLength
     */
    public Integer getMaxPremiseLength() {
        return maxPremiseLength;
    }

    /**
     * Número máximo de atributos en la premisa.
     * @param maxPremiseLength the maxPremiseLength to set
     */
    public void setMaxPremiseLength(Integer maxPremiseLength) {
        this.maxPremiseLength = maxPremiseLength;
    }

    /**
     * Número mínimo de atributos en la premisa.
     * @return the minPremiseLength
     */
    public Integer getMinPremiseLength() {
        return minPremiseLength;
    }

    /**
     * Número mínimo de atributos en la premisa.
     * @param minPremiseLength the minPremiseLength to set
     */
    public void setMinPremiseLength(Integer minPremiseLength) {
        this.minPremiseLength = minPremiseLength;
    }

    /**
     * Número máximo de atributos en la conclusión.
     * @return the maxConclusionLength
     */
    public Integer getMaxConclusionLength() {
        return maxConclusionLength;
    }

    /**
     * Número máximo de atributos en la conclusión.
     * @param maxConclusionLength the maxConclusionLength to set
     */
    public void setMaxConclusionLength(Integer maxConclusionLength) {
        this.maxConclusionLength = maxConclusionLength;
    }

    /**
     * Número mínimo de atributos en la conclusión.
     * @return the minConclusionLength
     */
    public Integer getMinConclusionLength() {
        return minConclusionLength;
    }

    /**
     * Número mínimo de atributos en la conclusión.
     * @param minConclusionLength the minConclusionLength to set
     */
    public void setMinConclusionLength(Integer minConclusionLength) {
        this.minConclusionLength = minConclusionLength;
    }

    /**
     * Tipo de los nodos.
     * @return the nodeType
     */
    public NodeType getNodeType() {
        return nodeType;
    }

    /**
     * Tipo de los nodos.
     * @param nodeType the nodeType to set
     */
    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * Si se dibuja el grafo.
     * @return the showImage
     */
    public Boolean getShowImage() {
        return showImage;
    }

    /**
     * Si se dibuja el grafo.
     * @param showImage the showImage to set
     */
    public void setShowImage(Boolean showImage) {
        this.showImage = showImage;
    }
    
    

    
    
    
}
