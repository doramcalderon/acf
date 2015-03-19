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
public class ResultModelValidation {
    
    
    private ResultValidation result;
    
    public ResultModelValidation(ResultValidation result) {
        this.result = result;
    }

    public ResultValidation getResult() {
        return result;
    }

    public void setResult(ResultValidation result) {
        this.result = result;
    }
    
    
    
}
