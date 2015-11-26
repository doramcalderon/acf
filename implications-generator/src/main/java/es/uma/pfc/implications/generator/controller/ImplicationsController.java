package es.uma.pfc.implications.generator.controller;

import com.google.common.base.Strings;
import es.uma.pfc.implications.generator.ImplicationsFactory;
import es.uma.pfc.implications.generator.events.SystemSaved;
import es.uma.pfc.implications.generator.i18n.GeneratorMessages;
import es.uma.pfc.implications.generator.i18n.I18n;
import es.uma.pfc.implications.generator.io.GeneratorImplicationalSystemIO;
import es.uma.pfc.implications.generator.model.ImplicationsModel;
import es.uma.pfc.implications.generator.model.AttributeType;
import es.uma.pfc.implications.generator.model.ResultValidation;
import es.uma.pfc.implications.generator.validation.IntegerTextParser;
import es.uma.pfc.implications.generator.view.GenerateService;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.commons.files.FileUtils;
import es.uma.pfc.is.commons.strings.StringUtils;
import fr.kbertet.lattice.ImplicationalSystem;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implications generator view's controller.
 *
 * @author Dora Calderón
 */
public class ImplicationsController implements Initializable {
    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger (ImplicationsController.class);

    @FXML
    private TextField txtNodes;
    @FXML
    private TextField txtImplications;
    @FXML
    private TextField txtMinLongPremisse;
    @FXML
    private TextField txtMaxLongPremisse;
    @FXML
    private TextField txtMinLongConclusion;
    @FXML
    private TextField txtMaxLongConclusion;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnGenerate;
    @FXML
    private ComboBox cbNodeType;
    @FXML
    private TextField txtSystemsNumber;

    @FXML
    private TextArea textViewer;
    @FXML
    private TextField txtOutput;
    @FXML
    private AnchorPane implicationsPane;

    @FXML
    private ProgressIndicator generationProgressInd;

    /**
     * Nodes type.
     */
    private AttributeType nodeType;
    /**
     * Generated system.
     */
    private List<ImplicationalSystem> implicationSystems;
    /**
     * Model.
     */
    private ImplicationsModel model;

    /**
     * Validation support.
     */
    private ValidationSupport validationSupport;
    /**
     * Type of class which call to this controller.
     */
    private Class<?> callerType;

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
        initView();
        initModel();
        initBinding();
        initValidation();
        initListeners();
        ImplicationsFactory.initialize();
    }
    
    /**
     * Initializes the model.
     */
    protected void initModel() {
        model = new ImplicationsModel();
    }
    /**
     * Sets the output file path into the output field.
     * @param outputPath Output file path.
     */
    public void setOutput(String outputPath) {
        txtOutput.setText(outputPath);
    }
    /**
     * Sets the caller class of implications generator.
     * @param callerType Caller class.
     * @return This instance.
     */
    public ImplicationsController calledBy(Class<?> callerType) {
        this.callerType = callerType;
        return this;
    }
    
    /**
     * Inititalizes bindings.
     */
    protected void initBinding() {
        
        BooleanBinding nSystemsBinding = new BooleanBinding() {
            {super.bind(txtSystemsNumber.textProperty(), txtOutput.textProperty());}
            
            @Override
            protected boolean computeValue() {
                final int systemNumbers = (StringUtils.isNumeric(txtSystemsNumber.getText())) ? 
                                    Integer.valueOf(txtSystemsNumber.getText()) : 0;
                return (systemNumbers > 1) && StringUtils.isEmpty(txtOutput.getText());
            }
            
        };
        btnGenerate.disableProperty().bind(nSystemsBinding);
        txtOutput.textProperty().bindBidirectional(model.outputProperty());
        BooleanBinding systemCreatedBinding = new BooleanBinding() {
            {super.bind(model.systemCreatedProperty(), txtOutput.textProperty());}

            @Override
            protected boolean computeValue() {
                // TODO no se ejecuta el listener cuando se establece la propiedad systemcreated
                //return !model.isSystemCreated() || StringUtils.isEmpty(txtOutput.getText());
                return StringUtils.isEmpty(txtOutput.getText());
            }
        };
        btnSave.disableProperty().bind(systemCreatedBinding);
    }
    protected void initView() {
        this.cbNodeType.getItems().addAll(AttributeType.NUMBER, AttributeType.LETTER, AttributeType.INDEXED_LETTER);
        this.cbNodeType.getSelectionModel().select(AttributeType.NUMBER);

        IntegerTextParser parser = new IntegerTextParser();
        txtNodes.setTextFormatter(new TextFormatter<>(parser));
        txtImplications.setTextFormatter(new TextFormatter<>(parser));
        txtMaxLongPremisse.setTextFormatter(new TextFormatter<>(parser));
        txtMinLongPremisse.setTextFormatter(new TextFormatter<>(parser));
        txtMaxLongConclusion.setTextFormatter(new TextFormatter<>(parser));
        txtMinLongConclusion.setTextFormatter(new TextFormatter<>(parser));
        txtSystemsNumber.setTextFormatter(new TextFormatter<>(parser));
    }

    /**
     * Initializes the listeners.
     */
    protected void initListeners() {
        txtNodes.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            validationSupport.getValidationDecorator().removeDecorations(txtNodes);
        });
        txtImplications.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            validationSupport.getValidationDecorator().removeDecorations(txtImplications);
        });
        txtMinLongPremisse.textProperty().addListener((
                ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    handlePremisseSizeChange();
                });
        txtMaxLongPremisse.textProperty().addListener((
                ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    handlePremisseSizeChange();
                });
        txtMinLongConclusion.textProperty().addListener((
                ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    handleConclusionSizeChange();
                });
        txtMaxLongConclusion.textProperty().addListener((
                ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    handleConclusionSizeChange();
                });
        
    }

    protected void initValidation() {
        validationSupport = new ValidationSupport();
    }

    /**
     * Handles the event thrown when the Clean button is pressed.<br/>
     * Cleans the form fields.
     * @param event Event.
     */
    @FXML
    public void clean(ActionEvent event) {
        cleanView();
        cleanModel();
    }

    /**
     * Updates the node type to use form implications.
     * @param event Event.
     */
    @FXML
    public void handleNodeTypeSelection(ActionEvent event) {
        nodeType = (AttributeType) cbNodeType.getSelectionModel().getSelectedItem();
    }

    /**
     * Cleans the decorators of max and min premisse length.
     */
    public void handlePremisseSizeChange() {
        validationSupport.getValidationDecorator().removeDecorations(txtMinLongPremisse);
        validationSupport.getValidationDecorator().removeDecorations(txtMaxLongPremisse);
    }

    /**
     * Cleans the decorators of max and min conclusion length.
     */
    public void handleConclusionSizeChange() {
        validationSupport.getValidationDecorator().removeDecorations(txtMinLongConclusion);
        validationSupport.getValidationDecorator().removeDecorations(txtMaxLongConclusion);
    }

    /**
     * Handles the event thrown when the Generate button is pressed.<br/>
     * Generates the implications with the inserted values.
     *
     * @param event Event.
     */
    @FXML
    public void handleGenerateButton(ActionEvent event) {

        try {
            viewToModel();
            validate();

            GenerateService genService = new GenerateService(model);
            genService.setOnRunning((WorkerStateEvent event1) -> {
                implicationsPane.getScene().setCursor(Cursor.WAIT);
            });
            genService.setOnSucceeded((WorkerStateEvent event1) -> {
                implicationSystems = (List<ImplicationalSystem>) event1.getSource().getValue();
                implicationsGenerated(implicationSystems);
                implicationsPane.getScene().setCursor(Cursor.DEFAULT);
            });
            generationProgressInd.visibleProperty().bind(genService.runningProperty());
            genService.start();

        } catch (RuntimeException modelEx) {
            // TODO pintar el mensaje en la interfaz
            logger.error(modelEx.getMessage());
        }
    }

    /**
     * Shows the generated system in the viewer, if was generated only one.<br/>
     * If was generated several, shows the <i>Save</i> dialog box.
     *
     * @param systems Implicaciones generadas.
     */
    public void implicationsGenerated(List<ImplicationalSystem> systems) {
        if (systems != null && !systems.isEmpty()) {
            if (systems.size() == 1) {
                showText(systems.get(0));
                model.systemCreatedProperty().setValue(true);
            } else {
                save();
            }
        }
    }

    /**
     * Open the dialog box to select an output file, and copy the selected file path into Output field.
     * @param event Event.
     */
    @FXML
    public void handleOutputAction(ActionEvent event) {
        File selectedFile = showSaveDialog();
        if (selectedFile != null) {
            txtOutput.setText(selectedFile.getAbsolutePath());
        } else {
            txtOutput.clear();
        }
    }

    /**
     * Saves the implicational system in the path inserted into Output filed.
     * @param event Event.
     */
    @FXML
    public void handleSaveButton(ActionEvent event) {
        save();
    }

    /**
     * Saves the generated implicational systems.
     */
    protected void save() {
        String selectedFile = txtOutput.getText();

        if (selectedFile != null) {
            Task saveTask = new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                    try {
                        GeneratorImplicationalSystemIO.save(implicationSystems, selectedFile);
                    } catch (IOException ex) {
                        logger.error("Error saving the system.", ex);
                    }
                    return null;
                }

                @Override
                protected void succeeded() {
                    String path = txtOutput.getText(0, txtOutput.getText().lastIndexOf(File.separator)).replace(File.separator, "\\\\");
                    String message = GeneratorMessages.get().getMessage(I18n.SYSTEM_GENERATED,  txtSystemsNumber.getText(), path);
                    new Alert(AlertType.INFORMATION, message).showAndWait();
                    publicSystemsGenerated(selectedFile);
                    clean(null);
                }

                @Override
                protected void failed() {
                    logger.error("System generation error", getException());
                }
                
                

            };
            generationProgressInd.visibleProperty().bind(saveTask.runningProperty());
            saveTask.run();

        }
    }

    /**
     * Publishes a SystemSaved event by Eventbus.
     * @param selectedFile Output file path.
     */
    protected void publicSystemsGenerated(String selectedFile) {
        SystemSaved event = new SystemSaved();
        event.setCalledBy(callerType);
        if (model.getNum() == 1) {
            event.setPath(selectedFile);
        } else {
            String [] paths = new String[model.getNum()];
            for(int i = 1; i <= model.getNum(); i++) {
                paths[i-1] = FileUtils.getFileName(selectedFile, i);
            }
            event.setPath(paths);
        }
        Eventbus.post(event);
        
    }
    /**
     * Shows the "Save" dialog box and returns the selected path.
     * @return Selected file.
     */
    protected File showSaveDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Java Library Format", "*.txt"));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Prolog File", "*.pl"));
        fileChooser.setInitialFileName("implications.txt");
        Window mainStage = implicationsPane.getScene().getWindow();
        File selectedFile = fileChooser.showSaveDialog(mainStage);
        return selectedFile;
    }

    /**
     * Shows in the viewer an implicational system.
     * @param system Implicational system.
     */
    protected void showText(ImplicationalSystem system) {
        if (system != null) {
            textViewer.setText(system.toString());
        }
    }

    /**
     * Shows in the viewer a saved system.
     * @param implicationsFile Implicational system.
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
            logger.error("IO Exception", ex);
        }
    }

    /**
     * Performs the model validation.
     * @throw RuntimeException If validation error exists.
     */
    protected void validate() {
        ResultValidation validation = ResultValidation.OK;

        if (model != null) {
            validation = model.validate().getResult();
        }

        if (!validation.isValid()) {
            String message = validation.toString();

            if (ResultValidation.ZERO_NODES.equals(validation)) {
                validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(txtNodes, message));

            } else if (ResultValidation.INVALID_IMPLICATIONS_NUM.equals(validation)) {
                validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(txtImplications, message));

            } else if (ResultValidation.INVALID_PREMISE_LENGTH.equals(validation)) {
                validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(txtMaxLongPremisse, message));
                validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(txtMinLongPremisse, message));

            } else if (ResultValidation.INVALID_CONCLUSION_LENGTH.equals(validation)) {
                validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(txtMaxLongConclusion, message));
                validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(txtMinLongConclusion, message));

            }
            throw new RuntimeException("Error de validación: " + validation.toString());
        }
    }

    /**
     * Reads the view values and saves them into the model.
     */
    protected void viewToModel() {
        String strRulesNumber = txtImplications.getText();
        String strNodesNumber = txtNodes.getText();
        String strMinPremise = txtMinLongPremisse.getText();
        String strMaxPremise = txtMaxLongPremisse.getText();
        String strMinConclusion = txtMinLongConclusion.getText();
        String strMaxConclusion = txtMaxLongConclusion.getText();
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
     * Cleans the view fields.
     */
    protected void cleanView() {
        txtNodes.clear();
        txtImplications.clear();
//        imageViewer.setImage(null);
        this.cbNodeType.getSelectionModel().clearSelection();
        this.cbNodeType.getSelectionModel().select(AttributeType.NUMBER);
        this.txtMinLongPremisse.clear();
        this.txtMaxLongPremisse.clear();
        this.txtMinLongConclusion.clear();
        this.txtMaxLongConclusion.clear();
        this.txtSystemsNumber.clear();
        this.txtOutput.clear();
        textViewer.setText("<No implications generated>");
    }

    /**
     * Limpia el modelo.
     */
    protected void cleanModel() {
        if (model != null) {
            this.model.clean();
        }
        if (implicationSystems != null) {
            this.implicationSystems.clear();
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
