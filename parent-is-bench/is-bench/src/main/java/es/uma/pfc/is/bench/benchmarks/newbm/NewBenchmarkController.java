package es.uma.pfc.is.bench.benchmarks.newbm;

import com.google.common.eventbus.Subscribe;
import es.uma.pfc.implications.generator.controller.ImplicationsController;
import es.uma.pfc.implications.generator.events.SystemSaved;
import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.ISBenchApp;
import es.uma.pfc.is.bench.MainLayoutController;
import es.uma.pfc.is.bench.business.BenchmarksBean;
import es.uma.pfc.is.bench.domain.Benchmark;
import es.uma.pfc.is.algorithms.AlgorithmInfo;
import es.uma.pfc.is.bench.events.AlgorithmChangeEvent;
import es.uma.pfc.is.bench.events.AlgorithmsSelectedEvent;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.events.MessageEvent;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.i18n.I18n;
import es.uma.pfc.is.bench.services.BenchmarkSaveService;
import es.uma.pfc.is.bench.uitls.Chooser;
import es.uma.pfc.is.bench.uitls.Dialogs;
import es.uma.pfc.is.bench.view.FXMLViews;
import es.uma.pfc.is.commons.strings.StringUtils;
import es.uma.pfc.is.bench.config.WorkspaceManager;
import es.uma.pfc.is.bench.domain.ws.Preferences;
import es.uma.pfc.is.bench.services.AlgorithmsClassLoadService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.controlsfx.validation.Validator;

/**
 * NewBenchmark view Controller class.
 *
 * @author Dora Calderón
 */
public class NewBenchmarkController extends Controller {
    /**
     * Name field.
     */
    @FXML
    private TextField txtName;
    /**
     * Container of inputs implicational systems selected.
     */
    @FXML
    private TitledPane inputsTitledPane;
    /**
     * Label with selected files count.
     */
    @FXML
    private Label lbSelectedFiles;
    /**
     * List of selected input files.
     */
    @FXML
    private ListView inputsList;

    /**
     * Algorithms filter.
     */
    @FXML
    private TextField txtFilter;
    /**
     * Available algorithms.
     */
    @FXML
    private ListView algorithmsList;
    /**
     * Algorithms selected.
     */
    @FXML
    private ListView algorithmsSelected;
    /**
     * Root pane.
     */
    @FXML
    private AnchorPane rootPane;

    /**
     * Model.
     */
    private NewBenchmarkModel model;
    /**
     * Workspace manager.
     */
    private WorkspaceManager wsManager;
    /**
     * Delegate.
     */
    private BenchmarksDelegate benchmarksDelegate;

    /**
     * Initializes the controller class.
     *
     * @param url URL of the view.
     * @param rb Resource bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            super.initialize(url, rb);
            wsManager = WorkspaceManager.get();
            benchmarksDelegate = new BenchmarksDelegate();
            initView();
            initModel();
            initBinding();
            initListeners();
            initValidation();
            modelToView();
        } catch (IOException ex) {
            Logger.getLogger(NewBenchmarkController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Initializes the inputsList selection mode.
     * @throws IOException 
     */
    @Override
    protected void initView() throws IOException {
        inputsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    
    /**
     * Initializes the model.
     */
    @Override
    protected void initModel() {
        model = new NewBenchmarkModel();
        AlgorithmsClassLoadService loadService = new AlgorithmsClassLoadService();
        loadService.setOnSucceeded((WorkerStateEvent event) -> {
            List<AlgorithmInfo> algorithms = (List<AlgorithmInfo>) event.getSource().getValue();
            model.algorithmsListProperty().clear();
            if (algorithms != null) {
                model.algorithmsListProperty().set(FXCollections.observableArrayList(algorithms));
            }
            modelToView();
        });
        loadService.restart();
    }
    
    /**
     * Initializes the binding between view components and the model.
     */
    @Override
    protected void initBinding() {
        txtName.textProperty().bindBidirectional(model.nameProperty());
        inputsList.itemsProperty().bind(model.inputFilesListProperty());
        inputsList.disableProperty().bind(new BooleanBinding() {
            {
                super.bind(inputsList.itemsProperty());
            }
            
            @Override
            protected boolean computeValue() {
                return (inputsList.getItems().size() == 0);
            }
        });
        lbSelectedFiles.textProperty().bind(new StringBinding() {
            {
                super.bind(inputsList.itemsProperty());
            }
            
            @Override
            protected String computeValue() {
                return BenchMessages.get().getMessage(I18n.SELECTED_FILES_COUNT, inputsList.getItems().size());
            }
        });
        algorithmsSelected.itemsProperty().bindBidirectional(model.algorithmsSelectedProperty());
    }
    
    /**
     * Initializes the listeners.
     */
    @Override
    protected void initListeners() {
        Eventbus.register(this);
        txtName.focusedProperty().addListener((observable, oldFocused, focused) -> {
            if (!focused) {
                try {
                    Benchmark b = benchmarksDelegate.getBenchmark(model.getName());
                    if (b != null) {
                        File inputsDir = Paths.get(b.getInputsDir()).toFile();
                        model.inputFilesListProperty().set(FXCollections.observableArrayList(inputsDir.listFiles()));
                        model.algorithmsSelectedProperty().set(FXCollections.observableArrayList(b.getAlgorithmsEntities()));
                        
                    }
                } catch (Exception ex) {
                    Logger.getLogger(NewBenchmarkController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        txtName.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isEmpty = StringUtils.isEmpty(newValue);
            inputsTitledPane.setDisable(isEmpty);
            if(!isEmpty) {
                String inputDir = Paths.get(wsManager.currentWorkspace().getLocation(), newValue.trim(), "input").toString();
                model.inputProperty().set(inputDir);
            }
        });
        txtFilter.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    model.getAlgorithmsFilteredList().setPredicate(algorithm -> {
                        return (StringUtils.isEmpty(newValue) || StringUtils.containsIgnoreCase(algorithm.getShortName(), newValue));
                    });
                });
        
    }
    
    /**
     * Initializes the validation support.
     */
    @Override
    protected void initValidation() {
        getValidationSupport().registerValidator(txtName,
                Validator.createEmptyValidator(getI18nMessage(BenchMessages.EMPTY_ALGORITHM_NAME)));
    }
    
    /**
     * Load the algorithms list with algorihtms of the model.
     */
    @Override
    protected void modelToView() {
        algorithmsList.setItems(model.getAlgorithmsFilteredList());
    }

    /**
     * Form validations.
     *
     * @return {@code true} if there is one algorithm selected at least, and the benchmark name not exists or user wants
     * override it, and parent validations is succeeded. {@code false} otherwise.
     */
    @Override
    protected boolean validate() {
        if (algorithmsSelected.getItems().isEmpty()) {
            publicMessage(getI18nMessage(BenchMessages.EMPTY_ALGORITHM_LIST), MessageEvent.Level.ERROR);
            return false;
        }
        
        boolean validBenchmarkName = !new BenchmarksBean().exists(model.getName(), wsManager.currentWorkspace().getLocation());
        if (!validBenchmarkName) {
            Optional<ButtonType> confirm
                    = showAlert(Alert.AlertType.CONFIRMATION, null, getI18nMessage(BenchMessages.DUPLICATED_BENCHMARK));
            
            if (confirm.isPresent() && confirm.get().equals(ButtonType.CANCEL)) {
                return false;
            }
        }
        return super.validate();
    }
    
    /**
     * Reloads the view and model.
     */
    protected void reload() {
        initModel();
    }
    /**
     * Root pane.
     * @return Pane.
     */
    @Override
    protected Pane getRootPane() {
        return rootPane;
    }
    /**
     * Handles the AlgorithmsSelectedEvent published by the Eventbus.<br/>
     * Adds all algorithms contained in the event to algorithms selected list.
     * @param event Event.
     */
    @Subscribe
    public void algorithmsSelected(AlgorithmsSelectedEvent event) {
        if (event.getAlgorithmsSelection() != null) {
            algorithmsSelected.getItems().addAll(event.getAlgorithmsSelection());
        }
    }
    /**
     * Handles the AlgorithmChangeEvent published by the Eventbus.<br/>
     * Reloads the model and view.
     * @param event Event.
     */
    @Subscribe
    public void handleAlgorithmChanges(AlgorithmChangeEvent event) {
        reload();
    }

    /**
     * Handles the {@link SystemSaved} event, copying the path of system into input field.
     *
     * @param event Event.
     */
    @Subscribe
    public void handleSystemSaved(SystemSaved event) {
        String[] paths = event.getPaths();
        if (paths != null) {
            Arrays.stream(paths)
                    .forEach(path -> model.inputFilesListProperty().get().add(Paths.get(path).toFile()));
        }
    }
    /**
     * Shows the Open File dialog box for select one or more JARs library which will be added to lib directory 
     * in the current workspace.
     * @param action Event thrown when the Add Lib button is pressed.
     */
    @FXML
    protected void handleNewAlgorithm(ActionEvent event) {
        File homeDir = Paths.get(WorkspaceManager.get().currentWorkspace().getLocation()).toFile();
        List<File> jars = Chooser.openMultipleFileChooser(getRootPane().getScene().getWindow(),
                Chooser.FileChooserMode.OPEN, getI18nLabel(I18n.SELECT_JAR_DIALOG_TITLE), homeDir,
                new FileChooser.ExtensionFilter(getI18nLabel(I18n.JAR_FILE), "*.jar"));
        
        Path targetDir = Paths.get(WorkspaceManager.get().getPreference(Preferences.ALGORITHMS_PATH));
        
        if(jars != null) {
            for(File jar : jars) {
                try {
                    Path targetFile = Paths.get(targetDir.toString(), jar.getName());
                    if(Files.exists(targetFile)) {
                        showAlert(Alert.AlertType.ERROR, 
                                  BenchMessages.get().getMessage(BenchMessages.FILE_EXISTS_TITLE), 
                                  BenchMessages.get().getMessage(BenchMessages.FILE_EXISTS));
                        
                    } else {
                        Files.copy(jar.toPath(), targetFile, StandardCopyOption.COPY_ATTRIBUTES,  StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(NewBenchmarkController.class.getName())
                            .log(Level.SEVERE, getI18nMessage(BenchMessages.COPYING_JARS_ERROR, jar, targetDir), ex);
                }
            }
            reload();
        }
    }

    /**
     * Shows a file chooser for select the input system file.
     *
     * @param event Action event.
     */
    @FXML
    protected void handleSelectInputDir(ActionEvent event) {
        File defaultInputDir = Paths.get(wsManager.currentWorkspace().getLocation()).toFile();
        
        List<File> resultFile = Chooser.openMultipleFileChooser(getRootPane().getScene().getWindow(),
                Chooser.FileChooserMode.OPEN, "Input system", defaultInputDir,
                new FileChooser.ExtensionFilter(getI18nLabel(I18n.TEXT_FILE), "*.txt"),
                new FileChooser.ExtensionFilter(getI18nLabel(I18n.PROLOG_FILE), "*.pl"));
        if (resultFile != null) {
            List<File> selectedFiles = new ArrayList(model.getInputFilesList());
            selectedFiles.addAll(resultFile);
            model.inputFilesListProperty().setValue(FXCollections.observableArrayList(selectedFiles));
        }
    }

    /**
     * Shows the generator panel for generate a random system.
     *
     * @param event Action event.
     */
    @FXML
    public void handleGenerateSystem(ActionEvent event) {
        try {
            String implicationsPath = Paths.get(model.getInputDir(), "implications.txt").toString();
            FXMLLoader loader = new FXMLLoader(ISBenchApp.class.getResource("/" + es.uma.pfc.implications.generator.view.FXMLViews.IMPLICATIONS_VIEW),
                    ResourceBundle.getBundle("es.uma.pfc.implications.generator.i18n.labels", Locale.getDefault()));
            
            Pane generatorForm = loader.load();
            loader.<ImplicationsController>getController().setOutput(implicationsPath);
            String title = getI18nLabel("Implications Generator"); // TODO crear label
            Dialogs.showModalDialog(title, generatorForm, rootPane.getScene().getWindow());
        } catch (IOException ex) {
            Logger.getLogger(MainLayoutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * When there is a double click in algorithms list, the selection is added to algorithms selected.
     *
     * @param mouseEvent Mouse event.
     */
    @FXML
    protected void handleListDoubleClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            Eventbus.post(new AlgorithmsSelectedEvent(algorithmsList.getSelectionModel().getSelectedItems()));
        }
    }

    /**
     * When there is a double click in selected algorithms list, the selection is remove from algorithms selected.
     *
     * @param mouseEvent Mouse event.
     */
    @FXML
    protected void handleSelectedListDoubleClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            Object unselectedItem = algorithmsSelected.getSelectionModel().getSelectedItem();
            this.algorithmsSelected.getItems().remove(unselectedItem);
        }
    }
    /**
     * When the Save button is pressed, the current values are validated and saved.
     * @param event Action event.
     */
    @FXML
    protected void handleSaveButton(ActionEvent event) {
        if (validate()) {
            Benchmark benchmark = new Benchmark(model.getName(), model.getAlgorithmsSelectedList());
            benchmark.setInputsDir(model.getInputDir());
            benchmark.setInputFiles(model.getInputFilesList());
            BenchmarkSaveService service = new BenchmarkSaveService(benchmark);
            service.setOnSucceeded((WorkerStateEvent event1) -> {
                clear();
            });
            service.restart();
        }
    }
    /**
     * When the Clear button is pressed, the fields are cleared.
     * @param event Action event.
     */
    @FXML
    protected void handleClearButton(ActionEvent event) {
        clear();
        
    }
    /**
     * When the Add Algorithm button is pressed, the New Algorithm window is shown.
     * @param event Action event.
     */
    @FXML
    protected void handleAddAlgAction(ActionEvent event) {
        try {
            Parent algorithmsPane = FXMLLoader.load(MainLayoutController.class.getResource(FXMLViews.ALGORITHMS_VIEW), getBundle());
            String title = getI18nLabel(I18n.ALGORITHMS_DIALOG_TITLE);
            Dialogs.showModalDialog(title, algorithmsPane, rootPane.getScene().getWindow());
        } catch (IOException ex) {
            Logger.getLogger(NewBenchmarkController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Deletes the current selection in the inputs list.
     *
     * @param event Action event.
     */
    @FXML
    protected void handleDeleteInputAction(ActionEvent event) {
        Optional<ButtonType> confirm
                = showAlert(Alert.AlertType.CONFIRMATION, null, getI18nMessage(BenchMessages.DELETE_INPUTS_CONFIRM));
        
        if (confirm.isPresent() && confirm.get().equals(ButtonType.OK)) {
            ObservableList<File> selectedInputs = FXCollections.observableArrayList(inputsList.getSelectionModel().getSelectedItems());
            if (selectedInputs != null) {
                final String[] fileNames = new String[selectedInputs.size()];
                int i = 0;
                for (File f : selectedInputs) {
                    fileNames[i++] = f.getAbsolutePath();
                }
                Arrays.stream(fileNames).forEach(f -> {
                    try {
                        Files.delete(Paths.get(f));
                    } catch (IOException ex) {
                        Logger.getLogger(NewBenchmarkController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                
                inputsList.getItems().removeAll(selectedInputs);
            }            
        }
        
    }
    /**
     * Clears all fields.
     */
    protected void clear() {
        txtName.clear();
        inputsList.getItems().clear();
        algorithmsSelected.getItems().clear();
    }
    
}
