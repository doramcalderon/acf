/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench.uitls;

import es.uma.pfc.is.bench.config.ConfigManager;
import java.io.File;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class Chooser {
    public enum FileChooserMode {OPEN, SAVE};

    private Chooser() {
        
    }
    /**
     * Abre un selector de directorio y devuelve la selección del usuario.
     * @param owner Ventana padre.
     * @param mode Modo de apertura: OPEN, SAVE.
     * @param title Título de la ventana.
     * @param initialDirectory Directorio inicial con el que se abre.
     * @param filters Filtros que se aplica a los archivos a mostrar en el cuadro de diálogo.
     * @return Directorio seleccionado.
     */
    public static File openFileChooser(Window owner, FileChooserMode mode, String title, File initialDirectory, FileChooser.ExtensionFilter ... filters) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(initialDirectory);
        fileChooser.getExtensionFilters().addAll(filters);
        
        File selectedFile = null;
        if(FileChooserMode.SAVE.equals(mode)) {
            selectedFile = fileChooser.showSaveDialog(owner);
        } else {
            selectedFile = fileChooser.showOpenDialog(owner);
        }
        
        return selectedFile;
    }
    /**
     * Abre un selector de directorio y devuelve la selección del usuario.
     * @param owner Ventana padre.
     * @param title Título de la ventana.
     * @param initialDirectory Directorio inicial con el que se abre.
     * @return Directorio seleccionado.
     */
    public static File openDirectoryChooser(Window owner, String title, File initialDirectory) {
          DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle(title);
        dirChooser.setInitialDirectory(initialDirectory);
        
        File selectedDir = dirChooser.showDialog(owner);
        
        return selectedDir;
    }
}
