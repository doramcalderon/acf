
package es.uma.pfc.is.bench;

import es.uma.pfc.is.bench.config.WorkspaceManager;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.events.MessageEvent;
import es.uma.pfc.is.bench.events.MessageEvent.Level;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.uitls.Dialogs;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;

/**
 * Controller of a FXML View.
 */
public abstract class Controller implements Initializable {
    /**
     * Internationalization resources.
     */
    private ResourceBundle bundle;
    /**
     * Internationalization messages class.
     */
    protected BenchMessages messages;
    /**
     * Validation support.
     */
    private ValidationSupport validationSupport;

    /**
     * Initializes the controller.
     * @param location URL of the view.
     * @param resources Internationalization resources.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.bundle = resources;
        messages = BenchMessages.get();
        validationSupport = new ValidationSupport();
    }

    /**
     * Validation support.
     *
     * @return ValidationSupport.
     */
    public ValidationSupport getValidationSupport() {
        return validationSupport;
    }

    /**
     * View internationalization resources.
     * @return ResourceBundle.
     */
    public ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * Establish view internationalization resources.
     *
     * @param bundle Bundle.
     */
    protected void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    /**
     * Returns translated value.
     * @param key Key.
     * @return Translated value if exists, key otherwise.
     */
    public String getI18nLabel(String key) {
        String value = key;
        if (bundle.containsKey(key)) {
            value = bundle.getString(key);
        }
        return value;
    }

    /**
     * Returns a translated value with the args params. If the message contains "{}" tokens, these will be replaced 
     * by  args params.
     * @param key Key.
     * @param args Arguments to replace.
     * @return Translated value if exists, key otherwise.
     */
    public String getI18nMessage(String key, Object... args) {
        String value = key;
        if (messages.getBundle().containsKey(key)) {
            value = messages.getMessage(key, args);
        }
        return value;
    }

    /**
     * Hook method for get the root pane of the view.
     *
     * @return Root pane of the view. {@code null} if it isn't overrided.
     */
    protected Pane getRootPane() {
        return null;
    }

    /**
     * Closes the parent window.
     */
    protected void close() {
        Pane rootPane = getRootPane();
        if (rootPane != null) {
            ((Stage) rootPane.getScene().getWindow()).close();
        }
    }

    /**
     * Hook method for additionals view initializations.
     * @throws IOException 
     */
    protected void initView() throws IOException {
    }

    /**
     * Initializes the view-model bindings.
     */
    protected void initBinding() {
    }

    /**
     * Initializes the model.
     */
    protected void initModel() {
    }

    ;
    /**
     * Updates the view with the model values.
     */
    protected void modelToView() {
    }

    /**
     * Initializes the listeners.
     */
    protected void initListeners() {
    }

    /**
     * Validation initialization.
     */
    protected void initValidation() {
    }

    
    /**
     * Checks the validation support status. If not is valid, show the message errors in the state bar.
     * @return true if there is not validation errors, false otherwise.
     */
    protected boolean validate() {
        boolean isValid = !validationSupport.isInvalid();
        if (!isValid) {
            StringBuilder sb = new StringBuilder();
            validationSupport.getValidationResult().getMessages().stream()
                            .forEach(msg -> sb.append(msg.getText()).append("\n"));
            publicMessage(sb.toString(), Level.ERROR);
            
        }
        return isValid;
    }
    
    /**
     * Shows an alert and return the user response.
     * @param type Alert type.
     * @param title Title.
     * @param message Message
     * @return User response.
     */
    protected Optional<ButtonType> showAlert(Alert.AlertType type, String title, String message) {
         return Dialogs.alert(type, title, message, getRootPane().getScene().getWindow()).showAndWait();
    }
    
    /**
     * Publishes an event by Eventbus for show a message.
     * This event must be listened by state bar and this one, will show it.
     * @param message Message.
     * @param level Level (INFO, SUCCEEDED, WARNING, ERROR).
    */
    protected void publicMessage(String message, Level level) {
        Eventbus.post(new MessageEvent(message, level));
    }
}
