/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench;

import com.google.common.eventbus.Subscribe;
import es.uma.pfc.is.bench.events.BenchEventBus;
import es.uma.pfc.is.bench.events.NewBenchmarkEvent;
import es.uma.pfc.is.bench.events.RunBenchmarkEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * FXML Controller class
 *
 * @author Dora Calderón
 */
public class BenchmarksController extends Controller {
    @FXML
    private TabPane benchmarksTabPane;
    @FXML
    private Tab tabNew;

    @FXML
    private Tab tabRun;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        try {
            initView();
            initModel();
            initBinding();
            initListeners();
            initValidation();
        } catch (IOException ex) {
            Logger.getLogger(BenchmarksController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @Override
    protected void initView() throws IOException {
        showRunTab(null);
    }
    

    @Override
    protected void initListeners() {
        BenchEventBus.get().register(this);
    }
    
    @Subscribe
    public void showNewTab(NewBenchmarkEvent event) {
        benchmarksTabPane.getSelectionModel().select(tabNew);
    }
    
    @Subscribe
    public void showRunTab(RunBenchmarkEvent event) {
        benchmarksTabPane.getSelectionModel().select(tabRun);
    }
    
    
}
