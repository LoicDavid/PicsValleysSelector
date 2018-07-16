package view;

import java.io.File;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Popup window for errors.
 * @author Loïc David
 */
public class ErrorWindow extends Stage {
    
    private final Label message;
    
    /**
     * Create a new instance of ErrorWindow.
     * @param s The message to display.
     * @see ErrorWindow
     */
    public ErrorWindow(String s) {
        message = new Label(s);
        initialize();
    }

    /**
     * Initialize the ErrorWindow.<br>
     * Create components and implement interactions.
     */
    private void initialize() {
        
        // Create components.
        Button button = new Button("OK");
        button.setPrefWidth(50);
        
        // Create Box and add components.
        VBox vBox = new VBox(20);
        vBox.setPadding(new Insets(20, 20, 10, 20));
        vBox.setAlignment(Pos.CENTER); 
        vBox.getChildren().addAll(message, button);
        
        // Initialize window.
        String path = new File(".//.//ressources//errorlogo.png").toURI().toString();
        Image logoImage = new Image(path);
        getIcons().add(logoImage);
        setMinWidth(300);
        setTitle("Error!");
        Scene scene = new Scene(vBox);
        setScene(scene);
        setResizable(false);
        sizeToScene();
        
        // Add method to button.
        // Close the window.
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hide();
            }
        });
    }
    
}