package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 *
 * @author Loïc
 */
public class CoordinatesView extends HBox {
    
    private final GraphView graphView;
    
    public CoordinatesView(GraphView graphView) {
        this.graphView = graphView;
        initialize();
    }

    private void initialize() {
        
        setStyle("-fx-border-width: 1 0 0 0;-fx-border-color: black");
        
        Label cursorLabel = new Label("Cursor: 0, 0");
        Label dotLabel = new Label("Dot: None, None");
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        
        getChildren().addAll(cursorLabel, region, dotLabel);
        
        graphView.pointedChangeProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                DotView dot = graphView.getPointedDot();
                if (dot == null) {
                    dotLabel.setText("Dot: None, None");
                }
                else {
                    dotLabel.setText("Dot: " + (double)((int)(graphView.tViewToModel(dot.getCenterX()) * 10)) / 10 + ", " + (double)((int)(graphView.yViewToModel(dot.getCenterY()) * 100)) / 100);
                }
            }
        });
        
        graphView.xCursorProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                cursorLabel.setText("Cursor: " + newValue + ", " + graphView.yCursorProperty().getValue());
            }
        });
        graphView.yCursorProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                cursorLabel.setText("Cursor: " + graphView.xCursorProperty().getValue() + ", " + newValue);
            }
        });
        
    }
    
}
