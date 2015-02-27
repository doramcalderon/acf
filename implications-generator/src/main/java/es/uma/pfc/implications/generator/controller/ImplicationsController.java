/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.implications.generator.controller;

import com.google.common.base.Strings;
import es.uma.pfc.implications.generator.ImplicationsFactory;
import es.uma.pfc.implications.generator.model.ImplicationsModel;
import es.uma.pfc.implications.generator.model.NodeType;
import fr.kbertet.lattice.ImplicationalSystem;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author Dora Calderón
 */
public class ImplicationsController implements Initializable {

    @FXML
    private TextField txtNodes;
    @FXML
    private TextField txtImplications;
    @FXML
    private TextField txtMaxLongLeft;
    @FXML
    private TextField txtMaxLongRight;
    @FXML
    private Button btnSave;
    @FXML private ComboBox cbNodeType;
    
    @FXML
    private Text textViewer;
    @FXML
    private AnchorPane implicationsPane;

    private NodeType nodeType;
    ImplicationalSystem implications;
    
    private ImplicationsModel model;

//    @FXML
//    private ResourceBundle resources;
    
    /**
     * Initializes the controller class.
     * @param url URL.
     * @param rb Resource Bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        this.resources = rb;
        this.model = new ImplicationsModel();
        this.cbNodeType.getItems().addAll(NodeType.NUMBER, NodeType.LETTER, NodeType.INDEXED_LETTER);
        this.cbNodeType.getSelectionModel().select(NodeType.NUMBER);
    }

    /**
     * Controlador del botón Clean.<br/>
     * Limpia los campos.
     *
     * @param event ActionEvent.
     */
    @FXML
    public void clean(ActionEvent event) {
        txtNodes.clear();
        txtImplications.clear();
//        imageViewer.setImage(null);
        textViewer.setText("<No implications generated>");
        btnSave.setDisable(true);
        this.cbNodeType.getSelectionModel().clearSelection();
        this.cbNodeType.getSelectionModel().select(NodeType.NUMBER);
    }

    /**
     * Actualiza el tipo de nodo a utilizar para las implicaciones.
     * @param event Evento.
     */
    @FXML
    public void handleNodeTypeSelection(ActionEvent event) {
        nodeType = (NodeType) cbNodeType.getSelectionModel().getSelectedItem();
    }
    
    /**
     * Controlador del botón Generate. Genera las implicaciones con los valores introducidos.
     *
     * @param event ActionEvent.
     */
    @FXML
    public void handleGenerateButton(ActionEvent event) {
        int nodesNumber = new Integer(txtNodes.getText());
        int rulesNumber = new Integer(txtImplications.getText());
        String strMaxLongLeft = txtMaxLongLeft.getText();
        String strMaxLongRight = txtMaxLongRight.getText();
        
        Integer maxLongLeft = (!Strings.isNullOrEmpty(strMaxLongLeft)) ? new Integer(strMaxLongLeft) : null;
        Integer maxLongRight = (!Strings.isNullOrEmpty(strMaxLongRight)) ? new Integer(strMaxLongRight) : null;
        implications = ImplicationsFactory.getImplicationalSystem(nodesNumber, rulesNumber, maxLongLeft, maxLongRight, nodeType);
        
        showText(implications);
        btnSave.setDisable(false);

    }

    /**
     * Guarda las implicaciones generadas en un archivo.<br/>
     * Muestra un cuadro de diálogo para que el usuario seleccione la carpeta destino.
     *
     * @param event ActionEvent.
     */
    @FXML
    public void handleSaveButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Text File", "*.txt"));
        fileChooser.setInitialFileName("implications.txt");
        Window mainStage = implicationsPane.getScene().getWindow();
        File selectedFile = fileChooser.showSaveDialog(mainStage);
        if (selectedFile != null) {
            try {
                implications.save(selectedFile.getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(ImplicationsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Muestra la representación en texto del sistema generado.
     *
     * @param system Sistema de implicaciones.
     */
    protected void showText(ImplicationalSystem system) {
        if (system != null) {
            textViewer.setText(system.toString());
        }
    }

    /**
     * Muestra el sistema guardado en un archivo.
     *
     * @param implicationsFile Sistema de implicaciones.
     */
    protected void showText(File implicationsFile) {
        try {

            if (implicationsFile.exists()) {
                List<String> lines = Files.readAllLines(implicationsFile.toPath());
                StringBuilder sb = new StringBuilder();

                for (String line : lines) {
                    sb.append(line).append("\n");
                }
                textViewer.setText(sb.toString());
            }
        } catch (IOException ex) {
            Logger.getLogger(ImplicationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    /**
//     * Abre un archivo DOT como imagen.
//     *
//     * @param file Archivo DOT.
//     */
//    protected void showImage(File file) {
//        if (chkImage.isSelected()) {
//            InputStream is = null;
//            try {
//                is = DOT.runDOT(file, "png");
//                imageViewer.setImage(new Image(is));
//                imageViewer.setVisible(true);
//            } catch (IOException ex) {
//                Logger.getLogger(ImplicationsController.class.getName()).log(Level.SEVERE, null, ex);
//            } finally {
//                try {
//                    is.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(ImplicationsController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//
//        }
//
//    }

}
