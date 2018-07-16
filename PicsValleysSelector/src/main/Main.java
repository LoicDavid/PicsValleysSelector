package main;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Data;
import view.MainView;

/**
 *
 * @author Loïc
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        Data data = new Data();
        MainView mainView = new MainView(data);
        
        Scene scene = new Scene(mainView);
        
        primaryStage.setTitle("Pics and Valleys Selector");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
        
        data.inputFileProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                primaryStage.setTitle(newValue + " - Pics and Valleys Selector");
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
