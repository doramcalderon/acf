package es.uma.pfc.is.bench.benchmarks.newbm;

import com.google.common.eventbus.Subscribe;
import es.uma.pfc.implications.generator.controller.ImplicationsController;
import es.uma.pfc.implications.generator.events.SystemSaved;
import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.ISBenchApp;
import es.uma.pfc.is.bench.MainLayoutController;
import es.uma.pfc.is.bench.business.BenchmarksBean;
import es.uma.pfc.is.bench.domain.Benchmark;
import es.uma.pfc.is.bench.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.events.AlgorithmChangeEvent;
import es.uma.pfc.is.bench.events.AlgorithmsSelectedEvent;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.events.MessageEvent;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.i18n.I18n;
import es.uma.pfc.is.bench.services.AlgorithmsLoadService;
import es.uma.pfc.is.bench.services.BenchmarkSaveService;
import es.uma.pfc.is.bench.uitls.Chooser;
import es.uma.pfc.is.bench.uitls.Dialogs;
import es.uma.pfc.is.bench.view.FXMLViews;
import es.uma.pfc.is.commons.strings.StringUtils;
import es.uma.pfc.is.bench.domain.ws.WorkspaceManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.controlsfx.validation.Validator;

/**
 * FXML Controller class
 *
 * @author Dora Calderón
 */
public class NewBenchmarkController extends Controller {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtInput;
    @FXML
    private SplitMenuButton inputButton;
    /**
     * Algorithms filter.
     */
    @FXML
    private TextField txtFilter;
    @FXML
    private ListView algorithmsList;
    @FXML
    private ListView algorithmsSelected;

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
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            super.initialize(url, rb);
            wsManager = WorkspaceManager.get();
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

    @Override
    protected void initModel() {
        model = new NewBenchmarkModel();
        AlgorithmsLoadService loadService = new AlgorithmsLoadService();
        loadService.setOnSucceeded((WorkerStateEvent event) -> {
            Set<AlgorithmEntity> algorithms = (Set<AlgorithmEntity>) event.getSource().getValue();
            model.algorithmsListProperty().clear();
            if (algorithms != null) {
                model.algorithmsListProperty().set(FXCollections.observableArrayList(algorithms));
            }
            modelToView();
        });
        loadService.restart();
    }

    @Override
    protected void initBinding() {
        txtName.textProperty().bindBidirectional(model.nameProperty());
        txtInput.textProperty().bindBidirectional(model.inputProperty());
        algorithmsSelected.itemsProperty().bindBidirectional(model.algorithmsSelectedProperty());
    }

    @Override
    protected void initListeners() {
        Eventbus.register(this);

        txtFilter.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    model.getAlgorithmsFilteredList().setPredicate(algorithm -> {
                        return (StringUtils.isEmpty(newValue) || StringUtils.containsIgnoreCase(algorithm.getName(), newValue));
                    });
                });

    }

    @Override
    protected void initValidation() {
        getValidationSupport().registerValidator(txtName,
                Validator.createEmptyValidator(getI18nMessage(BenchMessages.EMPTY_ALGORITHM_NAME)));
    }

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

    protected void reload() {
        initModel();
    }

    @Override
    protected Pane getRootPane() {
        return rootPane;
    }

    @Subscribe
    public void algorithmsSelected(AlgorithmsSelectedEvent event) {
        if (event.getAlgorithmsSelection() != null) {
            algorithmsSelected.getItems().addAll(event.getAlgorithmsSelection());
        }
    }

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
        txtInput.setText(event.getPath());
    }
    
    @FXML
    private void handleNewAlgorithm(ActionEvent event) {
        try {
            Parent algorithmsPane = FXMLLoader.load(MainLayoutController.class.getResource(FXMLViews.ALGORITHMS_VIEW), getBundle());
            String title = getI18nLabel(I18n.ALGORITHMS_DIALOG_TITLE);
            Dialogs.showModalDialog(title, algorithmsPane, rootPane.getScene().getWindow());
        } catch (IOException ex) {
            Logger.getLogger(MainLayoutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Shows a file chooser for select the input system file.
     *
     * @param event Action event.
     */
    @FXML
    public void handleSelectInputFile(ActionEvent event) {
        File defaultInputDir = wsManager.getPreferenceAsFile("input.default");
        
        File resultFile = Chooser.openFileChooser(getRootPane().getScene().getWindow(),
                Chooser.FileChooserMode.OPEN, "Input system", defaultInputDir,
                new FileChooser.ExtensionFilter(getI18nLabel(I18n.TEXT_FILE), "*.txt"),
                new FileChooser.ExtensionFilter(getI18nLabel(I18n.PROLOG_FILE), "*.pl"));
        if (resultFile != null) {
            txtInput.setText(resultFile.getAbsolutePath());
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
            String implicationsPath = Paths.get(wsManager.currentWorkspace().getLocation(), model.getName(), "implications.txt").toString();
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

    @FXML
    protected void handleSaveButton(ActionEvent event) {
        if (validate()) {
            Benchmark benchmark = new Benchmark(model.getName(), model.getAlgorithmsSelectedList());
            benchmark.setInput(model.getInput());
            BenchmarkSaveService service = new BenchmarkSaveService(benchmark);
            service.setOnSucceeded((WorkerStateEvent event1) -> {
                clear();
            });
            service.restart();
        }
    }

    @FXML
    protected void handleClearButton(ActionEvent event) {
        clear();

    }

    @FXML
    public void handleAddAlgAction(ActionEvent event) {
        try {
            Parent algorithmsPane = FXMLLoader.load(MainLayoutController.class.getResource(FXMLViews.ALGORITHMS_VIEW), getBundle());
            String title = getI18nLabel(I18n.ALGORITHMS_DIALOG_TITLE);
            Dialogs.showModalDialog(title, algorithmsPane, rootPane.getScene().getWindow());
        } catch (IOException ex) {
            Logger.getLogger(NewBenchmarkController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void clear() {
        txtName.clear();
        txtInput.clear();
        algorithmsSelected.getItems().clear();
    }
}
