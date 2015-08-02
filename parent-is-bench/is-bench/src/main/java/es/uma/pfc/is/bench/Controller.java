/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench;

import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.events.MessageEvent;
import es.uma.pfc.is.bench.events.MessageEvent.Level;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.uitls.Dialogs;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;

/**
 *
 * @since @author Dora Calderón
 */
public abstract class Controller implements Initializable {

    private ResourceBundle bundle;
    protected BenchMessages messages;
    /**
     * Validation support.
     */
    private ValidationSupport validationSupport;

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
     * Bundle of view.
     *
     * @return Bundle.
     */
    public ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * Establish bundle of view.
     *
     * @param bundle Bundle.
     */
    protected void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    /**
     * Return internazionalized value.
     *
     * @param key Key.
     * @return Internazionalized value if exists, key otherwise.
     */
    public String getI18nLabel(String key) {
        String value = key;
        if (bundle.containsKey(key)) {
            value = bundle.getString(key);
        }
        return value;
    }

    /**
     * Return internazionalized value.
     *
     * @param key Key.
     * @return Internazionalized value if exists, key otherwise.
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
     * Close the parent window.
     */
    protected void close() {
        Pane rootPane = getRootPane();
        if (rootPane != null) {
            ((Stage) rootPane.getScene().getWindow()).close();
        }
    }

    protected void initView() throws IOException {
    }

    ;
    protected void initBinding() {
    }

    ;
    protected void initModel() {
    }

    ;
    /**
     * Update the view with the model values.
     */
    protected void modelToView() {
    }

    ;
    protected void initListeners() {
    }

    ;
    protected void initValidation() {
    }

    ;
    /**
     * Check the validation support status. If not is valid, show the message errors in the state bar.
     * @return 
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
     * Show an alert and return the user response.
     * @param type Alert type.
     * @param title Title.
     * @param message Message
     * @return User response.
     */
    protected Optional<ButtonType> showAlert(Alert.AlertType type, String title, String message) {
         return Dialogs.alert(type, title, message, getRootPane().getScene().getWindow()).showAndWait();
    }
    
    protected void publicMessage(String message, Level level) {
        Eventbus.post(new MessageEvent(message, level));
    }
}
