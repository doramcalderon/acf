
package es.uma.pfc.implications.generator.model;

import es.uma.pfc.implications.generator.Messages;
import es.uma.pfc.implications.generator.exception.ModelException;



/**
 * Clase que representa las características de un sistema de implicaciones.
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
     * @throws ModelException Si el número de nodos es 0.
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
     * @throws ModelException Si el número mínimo de atributos en la premisa está establecido y es mayor que el máximo.
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
     * @param minPremiseLength Número mínimo de atributos.
     * @throws ModelException Si el número máximo de atributos en la premisa está establecido y es menor que el mínimo.
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
    
    
    /**
     * Comprueba que el modelo es correcto para la generación de un sistema de implicaciones.
     * @return 
     */
    public ResultModelValidation validate() {
        ResultModelValidation result;
        
        if (nodes == null || nodes == 0) {
            result = new ResultModelValidation(ResultValidation.ZERO_NODES);
        } else if (maxPremiseLength != null && minPremiseLength != null && maxPremiseLength < minPremiseLength) {
            result = new ResultModelValidation(ResultValidation.INVALID_PREMISE_LENGTH);   
        } else  if (minPremiseLength != null && maxPremiseLength != null && minPremiseLength > maxPremiseLength) {
            result = new ResultModelValidation(ResultValidation.INVALID_PREMISE_LENGTH);   
        } else {
            result = new ResultModelValidation(ResultValidation.OK);
        }
               
        
        return result;
    }
    
    
    
}
