/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.implications.generator.controller;

import es.uma.pfc.implications.generator.view.FXMLViews;
import java.net.URL;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Dora Calderón
 */
public class ImplicationsGeneratorAppTest {
    
    public ImplicationsGeneratorAppTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testLoadRootFxml() {
        URL fxmlUrl = Thread.currentThread().getContextClassLoader().getResource(FXMLViews.ROOT_VIEW);
        assertNotNull(fxmlUrl);
    }
    
    @Test
    public void testLoadImplicationsFxml() {
        URL fxmlUrl = Thread.currentThread().getContextClassLoader().getResource(FXMLViews.IMPLICATIONS_VIEW);
        assertNotNull(fxmlUrl);
    }
}
