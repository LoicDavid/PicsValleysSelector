package view;

import javafx.scene.layout.VBox;
import model.Data;

/**
 *
 * @author Loïc
 */
public class MainView extends VBox {
    
    private final Data data;
    
    public MainView(Data data) {
        this.data = data;
        initialize();
    }

    private void initialize() {
        
        MenuView menuView = new MenuView(data);
        SelectedView selectedView = new SelectedView(data);
        GraphView graphView = new GraphView(data);
        CoordinatesView coordinatesView = new CoordinatesView(graphView);
        
        getChildren().addAll(menuView, selectedView, graphView, coordinatesView);
        
    }
    
}
