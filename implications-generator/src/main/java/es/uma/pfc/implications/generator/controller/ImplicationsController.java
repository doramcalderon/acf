/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.implications.generator.controller;

import com.google.common.base.Strings;
import es.uma.pfc.implications.generator.ImplicationsFactory;
import es.uma.pfc.implications.generator.io.GeneratorImplicationalSystemIO;
import es.uma.pfc.implications.generator.model.ImplicationsModel;
import es.uma.pfc.implications.generator.model.NodeType;
import es.uma.pfc.implications.generator.model.ResultValidation;
import es.uma.pfc.implications.generator.view.GenerateService;
import fr.kbertet.lattice.ImplicationalSystem;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.State.values;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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

    private Logger logger = Logger.getLogger(ImplicationsController.class.getName());

    @FXML
    private TextField txtNodes;
    @FXML
    private TextField txtImplications;
    @FXML
    private TextField txtMinLongLeft;
    @FXML
    private TextField txtMaxLongLeft;
    @FXML
    private TextField txtMinLongRight;
    @FXML
    private TextField txtMaxLongRight;
    @FXML
    private Button btnSave;
    @FXML
    private ComboBox cbNodeType;
    @FXML
    private TextField txtSystemsNumber;

    @FXML
    private Text textViewer;
    @FXML
    private AnchorPane implicationsPane;

    /**
     * Tipo de los nodos *
     */
    private NodeType nodeType;
    /**
     * Sistema generado.*
     */
    List<ImplicationalSystem> implicationSystems;

    ImplicationsModel model;

    private ProgressIndicator pin;

//    @FXML
//    private ResourceBundle resources;
    /**
     * Initializes the controller class.
     *
     * @param url URL.
     * @param rb Resource Bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        this.resources = rb;
        this.cbNodeType.getItems().addAll(NodeType.NUMBER, NodeType.LETTER, NodeType.INDEXED_LETTER);
        this.cbNodeType.getSelectionModel().select(NodeType.NUMBER);
//        pin = new ProgressIndicator();
//        pin.setProgress(1.0);
//        final HBox hb = new HBox();
//        hb.setSpacing(5);
//        hb.setAlignment(Pos.CENTER);
//        hb.getChildren().add(pin);

        ImplicationsFactory.initialize();
    }

    /**
     * Controlador del botón Clean.<br/>
     * Limpia los campos.
     *
     * @param event ActionEvent.
     */
    @FXML
    public void clean(ActionEvent event) {
        cleanView();
        cleanModel();
    }

    /**
     * Actualiza el tipo de nodo a utilizar para las implicaciones.
     *
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

        try {
            viewToModel();
            validate();

            GenerateService genService = new GenerateService(model);
            genService.setOnRunning((WorkerStateEvent event1) -> {
                implicationsPane.getScene().setCursor(Cursor.WAIT);
//                pin.setVisible(true);
            });
            genService.setOnSucceeded((WorkerStateEvent event1) -> {
                List<ImplicationalSystem> value = (List<ImplicationalSystem>) event1.getSource().getValue();
                implicationsGenerated(value);
                //                    pin.setVisible(false);
                implicationsPane.getScene().setCursor(Cursor.DEFAULT);
            });

            genService.start();
            implicationSystems = genService.getValue();

        } catch (RuntimeException modelEx) {
            // TODO pintar el mensaje en la interfaz
            logger.log(Level.SEVERE, modelEx.getMessage());
        }
    }

    /**
     * Muestra el conjunto generado en el visor, si sólo ha sido uno.<br/>
     * Si han sido varios, muestra el cuadro de diálogo <i>Guardar</i>.
     * @param systems Implicaciones generadas.
     */
    public void implicationsGenerated(List<ImplicationalSystem> systems) {
        if (systems != null && !systems.isEmpty()) {
            if (systems.size() == 1) {
                showText(systems.get(0));
                btnSave.setDisable(false);
            } else {
                save();
                clean(null);
            }
        }
    }

    /**
     * Guarda las implicaciones generadas en un archivo.<br/>
     * Muestra un cuadro de diálogo para que el usuario seleccione la carpeta destino.
     *
     * @param event ActionEvent.
     */
    @FXML
    public void handleSaveButton(ActionEvent event) {
        save();
    }

    /**
     * Guarda los conjuntos generados.
     */
    protected void save() {
        File selectedFile = showSaveDialog();

        if (selectedFile != null) {
            try {
                GeneratorImplicationalSystemIO.save(implicationSystems, selectedFile.getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(ImplicationsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Muestra el cuadro de diálogo "Guardar" y devuelve la ruta seleccionada.
     *
     * @return Archivo seleccionado.
     */
    protected File showSaveDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Text File", "*.txt"));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Prolog File", "*.pl"));
        fileChooser.setInitialFileName("implications.txt");
        Window mainStage = implicationsPane.getScene().getWindow();
        File selectedFile = fileChooser.showSaveDialog(mainStage);
        return selectedFile;
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

    /**
     * Valida el modelo.
     *
     * @throw RuntimeException Si hay algún error de validación.
     */
    public void validate() {
        ResultValidation validation = ResultValidation.OK;
        if (model != null) {
            validation = model.validate().getResult();
        }
        if (!validation.isValid()) {
            throw new RuntimeException("Error de validación: " + validation.toString());
        }
    }

    /**
     * Lee las propiedades de la vista y las guarda en el modelo.
     */
    public void viewToModel() {
        String strRulesNumber = txtImplications.getText();
        String strNodesNumber = txtNodes.getText();
        String strMinPremise = txtMinLongLeft.getText();
        String strMaxPremise = txtMaxLongLeft.getText();
        String strMinConclusion = txtMinLongRight.getText();
        String strMaxConclusion = txtMaxLongRight.getText();
        String strSystemsNumber = txtSystemsNumber.getText();

        Integer nodesNumber = (!Strings.isNullOrEmpty(strNodesNumber)) ? new Integer(strNodesNumber) : null;
        Integer rulesNumber = (!Strings.isNullOrEmpty(strRulesNumber)) ? new Integer(strRulesNumber) : null;
        Integer minPremiseLength = (!Strings.isNullOrEmpty(strMinPremise)) ? new Integer(strMinPremise) : null;
        Integer maxPremiseLength = (!Strings.isNullOrEmpty(strMaxPremise)) ? new Integer(strMaxPremise) : null;
        Integer minConclusionLength = (!Strings.isNullOrEmpty(strMinConclusion)) ? new Integer(strMinConclusion) : null;
        Integer maxConclusionLength = (!Strings.isNullOrEmpty(strMaxConclusion)) ? new Integer(strMaxConclusion) : null;
        Integer systemsNumber = (!Strings.isNullOrEmpty(strSystemsNumber)) ? new Integer(strSystemsNumber) : null;

        model = new ImplicationsModel(nodesNumber, rulesNumber);
        model.setNodeType(nodeType);
        model.setMinPremiseLength(minPremiseLength);
        model.setMaxPremiseLength(maxPremiseLength);
        model.setMinConclusionLength(minConclusionLength);
        model.setMaxConclusionLength(maxConclusionLength);
        model.setNum(systemsNumber);
    }

    /**
     * Limpia los datos introducidos de la vista.
     */
    protected void cleanView() {
        txtNodes.clear();
        txtImplications.clear();
//        imageViewer.setImage(null);
        textViewer.setText("<No implications generated>");
        this.cbNodeType.getSelectionModel().clearSelection();
        this.cbNodeType.getSelectionModel().select(NodeType.NUMBER);
        this.txtSystemsNumber.clear();
        btnSave.setDisable(true);
    }

    /**
     * Limpia el modelo.
     */
    protected void cleanModel() {
        this.model.clean();
        this.implicationSystems.clear();
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
