package view;

import java.io.File;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Data;

/**
 *
 * @author Loïc
 */
public class MenuView extends MenuBar {
    
    private final Data data;
    
    public MenuView(Data data) {
        this.data = data;
        initialize();
    }

    private void initialize() {
        
        Menu fileMenu = new Menu("File");
        MenuItem openMenuItem = new MenuItem("Open...");
        MenuItem saveMenuItem = new MenuItem("Save Pics and Valleys...");
        MenuItem quiteMenuItem = new MenuItem("Quite");
        
        fileMenu.getItems().addAll(openMenuItem, saveMenuItem, quiteMenuItem);
        getMenus().add(fileMenu);
        
        openMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select CSV file");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));
                File selectedFile = fileChooser.showOpenDialog(new Stage());
                if (selectedFile != null) {
                    try {
                        data.loadData(selectedFile.getPath().toString());
                    } catch (Exception ex) {
                        (new ErrorWindow("Can't load this file!")).show();
                    }
                }
            }
        });
        
        saveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select output CSV file");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));
                File selectedFile = fileChooser.showSaveDialog(new Stage());
                if (selectedFile != null) {
                    try {
                        data.saveData(selectedFile.getPath().toString());
                    } catch (Exception ex) {
                        (new ErrorWindow("Can't save data!")).show();
                    }
                }
            }
        });
        
        quiteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });
        
    }
    
}
