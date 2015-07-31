/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.bench.services.FileReaderService;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class FileReaderServiceTest {
    
    public FileReaderServiceTest() {
    }

    

    /**
     * Test of calculateReaded method, of class FileReaderService.
     */
    @Test
    public void testCalculateReaded() {
        long totalLength = 2048L;
        long sizeBlock = 1024L;
        long block = 1;
        
        long readed = new FileReaderService().calculateReaded(totalLength, sizeBlock, block++);
        
        assertEquals(50L, readed);
        assertEquals(2, block);
    }
    
}
